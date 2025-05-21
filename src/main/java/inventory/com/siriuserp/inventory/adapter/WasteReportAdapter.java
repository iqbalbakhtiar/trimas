package com.siriuserp.inventory.adapter;

import javolution.util.FastList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Rama Almer Felix
 */

@Setter
@Getter
@AllArgsConstructor
public class WasteReportAdapter {
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal in = BigDecimal.ZERO;
    private BigDecimal out = BigDecimal.ZERO;

    private String productCode;
    private String productName;
    private String uom;

    private Long openingSerial = 0L;
    private Long inSerial = 0L;
    private Long outSerial = 0L;
    private Long productId;

    private FastList<WasteReportAdapter> adapters = new FastList<>();

    /**
     * Waste Report getReports
     * Used in @{@link com.siriuserp.inventory.query.WasteReportQuery}
     */
    public WasteReportAdapter(BigDecimal quantity, BigDecimal in, BigDecimal out, Long openingSerial, Long inSerial, Long outSerial, String productCode, String productName, Long productId, String uom)
    {
        this.quantity = quantity;
        this.in = in;
        this.out = out;

        this.openingSerial = openingSerial;
        this.inSerial = inSerial;
        this.outSerial = outSerial;

        this.productCode = productCode;
        this.productName = productName;
        this.productId = productId;
        this.uom = uom;
    }

    public WasteReportAdapter() {

    }

    public BigDecimal getSum()
    {
        return getQuantity().add(getIn()).subtract(getOut());
    }

    public Long getClosingSerial() {
        return getOpeningSerial() + getInSerial() - getOutSerial();
    }

    // All GrandTotal Method
    public BigDecimal getGrandTotalQuantity() {
        BigDecimal tot = BigDecimal.ZERO;
        for (WasteReportAdapter a: adapters) {
            tot = tot.add(a.getQuantity());
        }
        return tot;
    }

    public BigDecimal getGrandTotalIn() {
        BigDecimal tot = BigDecimal.ZERO;
        for (WasteReportAdapter a: adapters) {
            tot = tot.add(a.getIn());
        }
        return tot;
    }

    public BigDecimal getGrandTotalOut() {
        BigDecimal tot = BigDecimal.ZERO;
        for (WasteReportAdapter a: adapters) {
            tot = tot.add(a.getOut());
        }
        return tot;
    }

    public Long getGrandTotalOpeningSerial() {
        long tot = 0L;
        for (WasteReportAdapter a: adapters) {
            tot += a.getOpeningSerial();
        }
        return tot;
    }

    public Long getGrandTotalInSerial() {
        long tot = 0L;
        for (WasteReportAdapter a: adapters) {
            tot += a.getInSerial();
        }
        return tot;
    }

    public Long getGrandTotalOutSerial() {
        long tot = 0L;
        for (WasteReportAdapter a: adapters) {
            tot += a.getOutSerial();
        }
        return tot;
    }

    public Long getGrandTotalClosingSerial() {
        return getGrandTotalOpeningSerial()
                + getGrandTotalInSerial()
                - getGrandTotalOutSerial();
    }

    public BigDecimal getGrandTotalSum() {
        return getGrandTotalQuantity()
                .add(getGrandTotalIn())
                .subtract(getGrandTotalOut());
    }
}
