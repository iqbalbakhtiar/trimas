package com.siriuserp.reporting.accounting.query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.siriuserp.accounting.adapter.SimpleAccountingReportAdapter;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.reporting.accounting.adapter.GLRegisterReportAdapter;
import com.siriuserp.sdk.db.AbstractAccountingReportQuery;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.utility.DecimalHelper;

import javolution.util.FastList;
@SuppressWarnings("unchecked")
public class GLRegisterReportAlterQuery  extends AbstractAccountingReportQuery 
{

	public GLRegisterReportAlterQuery(SimpleAccountingReportAdapter simpleAccountingReportAdapter)
    {
        setReportAdapter(simpleAccountingReportAdapter);
    }
	
	public List<GLRegisterReportAdapter> execute()
    {
        FastList<GLRegisterReportAdapter> list = new FastList<GLRegisterReportAdapter>();

        SimpleAccountingReportAdapter adapter = (SimpleAccountingReportAdapter)reportAdapter;

         GLRegisterReportAdapter result = null;

       StringBuilder register = new StringBuilder();
        register.append("SELECT detail FROM JournalEntryDetail detail ");
        
        register.append("WHERE detail.journalEntry.accountingPeriod.id in (:periods) ");
        register.append("AND detail.journalEntry.organization.id in(:orgs) ");
        register.append("ORDER BY detail.account.code, detail.journalEntry.entryDate , detail.journalEntry.entrySourceType ASC");

        Query entrys = getSession().createQuery(register.toString());
        entrys.setCacheable(true);
        entrys.setReadOnly(true);
    
        entrys.setParameterList("periods",adapter.getPeriods());
        entrys.setParameterList("orgs",adapter.getOrganizations());
        
        List<JournalEntryDetail> out = entrys.list();
     
            if(out != null && !out.isEmpty())
            {
            	Long tempAcc = null;
     
            	for(JournalEntryDetail detail : out){
            			
            				if(tempAcc == null){
                    			result = new GLRegisterReportAdapter((GLAccount)getSession().load(GLAccount.class,detail.getAccount().getId()));
                    			
                    			tempAcc = detail.getAccount().getId();
                    			
                    		}else if(tempAcc != detail.getAccount().getId()){
                    			
                    			if(result != null && (result.getOpeningcredit().compareTo(BigDecimal.ZERO) != 0 || result.getOpeningdebet().compareTo(BigDecimal.ZERO) != 0 || !result.getEntrys().isEmpty())){
                    			
                    				list.add(result);
      
                    				tempAcc = detail.getAccount().getId();
                    				
                    				result = new GLRegisterReportAdapter((GLAccount)getSession().load(GLAccount.class,detail.getAccount().getId()));
                    				
                    				
                    			}

                    			if(!list.contains(result)){
                    			
                    				result = new GLRegisterReportAdapter((GLAccount)getSession().load(GLAccount.class,detail.getAccount().getId()));
                    				list.add(result);
                    			}
                    		
                    		}
            				
            				if(detail.getJournalEntry().getEntrySourceType().equals(EntrySourceType.OPENING)){
            					
		        				if(detail.getPostingType().equals(GLPostingType.DEBET)){
		        					result.setOpeningdebet(result.getOpeningdebet().add(detail.getAmount()));
		        				}else{
		        					result.setOpeningcredit(result.getOpeningcredit().add(detail.getAmount()));
		        				}
		        			}else if(detail.getJournalEntry().getEntrySourceType().equals(EntrySourceType.CLOSING)){
		        			
		        				if(detail.getPostingType().equals(GLPostingType.DEBET)){
		        					result.setClosingdebet(result.getClosingdebet().add(detail.getAmount()));
		        				}else{
		        					result.setClosingcredit(result.getClosingcredit().add(detail.getAmount()));
		        				}
		        			}else{
		        	
			        			 JournalEntryDetail clone = new JournalEntryDetail();
			                     clone.setAccount(detail.getAccount());
			                     clone.setClosingAccountType(detail.getClosingAccountType());
			                     clone.setJournalEntry(detail.getJournalEntry());
			                     clone.setNote(detail.getNote());
			                     clone.setPostingType(detail.getPostingType());
			                     clone.setTransactionDate(detail.getTransactionDate());
			                     clone.setAmount(detail.getAmount());
		                   
			                    if(!detail.getJournalEntry().getCurrency().getId().equals(adapter.getCurrency())){
			                             clone.setAmount(detail.getAmount().multiply(loadExchange(detail.getJournalEntry().getEntryDate(),detail.getJournalEntry().getCurrency().getId(),adapter.getCurrency(),detail.getJournalEntry().getExchangeType())));
			                    }
		                    	result.getEntrys().add(clone);  
		            		}
            				
            		
    					}
            }
            
        return list;
    }

    private BigDecimal loadExchange(Date date,Long from,Long to,ExchangeType type)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ex.rate FROM Exchange ex ");
        builder.append("WHERE ex.validFrom <= :date ");
        builder.append("AND ex.from.id =:_from ");
        builder.append("AND ex.to.id =:_to ");
        builder.append("AND ex.type =:exchangeType ");
        builder.append("ORDER BY ex.validFrom DESC ");
        
        Query exchange = getSession().createQuery(builder.toString());
        exchange.setCacheable(true);
        exchange.setReadOnly(true);
        exchange.setParameter("date",date);
        exchange.setParameter("_from",from);
        exchange.setParameter("_to",to);
        exchange.setParameter("exchangeType",type);
        exchange.setMaxResults(1);

        Object object = exchange.uniqueResult();
        if(object != null)
            return (BigDecimal)object;

        return BigDecimal.ZERO;
    }

    @SuppressWarnings("unused")
	private GLRegisterReportAdapter doOpening(SimpleAccountingReportAdapter adapter,Long account)
    {
        if(adapter.getCurrency().equals(adapter.getDefaultCurrency().getId()))
            return doDefault(adapter, account);

        return doNonDefCurrency(adapter, account);
    }

    private GLRegisterReportAdapter doNonDefCurrency(SimpleAccountingReportAdapter adapter, Long account)
    {
        GLRegisterReportAdapter response = new GLRegisterReportAdapter();

        Query accountQry = getSession().createQuery("FROM GLAccount account WHERE account.id =:account");
        accountQry.setCacheable(true);
        accountQry.setReadOnly(true);
        accountQry.setParameter("account",account);

        response.setAccount((GLAccount)accountQry.uniqueResult());

        StringBuilder builder = new StringBuilder();
        builder.append("FROM JournalEntryDetail d ");
        builder.append("WHERE d.account.id =:account ");
        builder.append("AND d.journalEntry.accountingPeriod.id in(:periods) ");
        builder.append("AND d.journalEntry.organization.id in(:orgs) ");
        builder.append("AND d.postingType =:postingType ");
        builder.append("AND d.journalEntry.entrySourceType =:entrySourceType ");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
        query.setParameter("account",account);
        query.setParameterList("periods",adapter.getOpeningPeriods());
        query.setParameterList("orgs",adapter.getOrganizations());
        query.setParameter("entrySourceType",EntrySourceType.OPENING);
        query.setParameter("postingType",GLPostingType.DEBET);
        
        BigDecimal debet = BigDecimal.ZERO;
        
        for(JournalEntryDetail detail:(List<JournalEntryDetail>)query.list())
        {
            if(adapter.getCurrency() != null && !detail.getJournalEntry().getCurrency().getId().equals(adapter.getCurrency()))
                debet = debet.add(detail.getAmount().multiply(loadExchange(detail.getJournalEntry().getEntryDate(),detail.getJournalEntry().getCurrency().getId(),adapter.getCurrency(),detail.getJournalEntry().getExchangeType())));
            else
                debet = debet.add(detail.getAmount());
        }

        response.setOpeningdebet(debet);

        query.setParameter("postingType",GLPostingType.CREDIT);
        
        BigDecimal credit = BigDecimal.ZERO;
        
        for(JournalEntryDetail detail:(List<JournalEntryDetail>)query.list())
        {
            if(adapter.getCurrency() != null && !detail.getJournalEntry().getCurrency().getId().equals(adapter.getCurrency()))
                credit = credit.add(detail.getAmount().multiply(loadExchange(detail.getJournalEntry().getEntryDate(),detail.getJournalEntry().getCurrency().getId(),adapter.getCurrency(),detail.getJournalEntry().getExchangeType())));
            else
                credit = credit.add(detail.getAmount());
        }

        response.setOpeningcredit(credit);

        return response;
    }

    private GLRegisterReportAdapter doDefault(SimpleAccountingReportAdapter adapter,Long account)
    {
        GLRegisterReportAdapter response = new GLRegisterReportAdapter();

        Query accountQry = getSession().createQuery("FROM GLAccount account WHERE account.id =:account");
        accountQry.setCacheable(true);
        accountQry.setReadOnly(true);
        accountQry.setParameter("account",account);

        response.setAccount((GLAccount)accountQry.uniqueResult());

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT SUM(d.amount*d.journalEntry.exchange.rate) FROM JournalEntryDetail d ");
        builder.append("WHERE d.account.id =:account ");
        builder.append("AND d.journalEntry.accountingPeriod.id in(:periods) ");
        builder.append("AND d.journalEntry.organization.id in(:orgs) ");
        builder.append("AND d.postingType =:postingType ");
        builder.append("AND d.journalEntry.entrySourceType =:entrySourceType ");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
        query.setParameter("account",account);
        query.setParameterList("periods",adapter.getOpeningPeriods());
        query.setParameterList("orgs",adapter.getOrganizations());
        query.setParameter("entrySourceType",EntrySourceType.OPENING);
        query.setParameter("postingType",GLPostingType.DEBET);

        response.setOpeningdebet(DecimalHelper.safe(query.uniqueResult()));

        query.setParameter("postingType",GLPostingType.CREDIT);

        response.setOpeningcredit(DecimalHelper.safe(query.uniqueResult()));

        return response;
    }
}

