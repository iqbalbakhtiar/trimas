/**
 * Aug 18, 2010 4:52:30 PM
 * com.siriuserp.tools.util
 * SiriusScafold.java
 */
package com.siriuserp.tools.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */
public class SiriusScafold
{
    public static void main(String[] args) throws IOException
    {
        if(args != null && args.length > 1 )
        {
            File model = new File("d:\\scafold\\"+args[0]+".java");
            model.createNewFile();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(model));

            writer.write("@Entity\n");
            writer.write("@Table(name=\""+args[0].toLowerCase()+"\")\n");
            writer.write("@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)\n");
            writer.write("public class "+args[0]+" extends Model\n");
            writer.write("{\n");
            for(int idx=1;idx<args.length;idx++)
                writer.write("\tprivate String "+args[idx]+";\n");
            
            writer.write("}");
            
            writer.flush();
            writer.close();
        }
    }
}
