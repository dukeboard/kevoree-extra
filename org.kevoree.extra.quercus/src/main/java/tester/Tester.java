package tester;

import com.caucho.quercus.Quercus;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.lib.*;
import com.caucho.quercus.lib.regexp.RegexpModule;
import com.caucho.quercus.lib.string.StringModule;
import com.caucho.quercus.page.QuercusPage;
import com.caucho.vfs.StdoutStream;
import com.caucho.vfs.StringStream;
import com.caucho.vfs.WriteStream;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 13/12/11
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class Tester {

    public static void main(String[] args) {


        try {


            Quercus quercus = new Quercus();
            quercus.init();
            quercus.start();
            quercus.setCompile(true);

            quercus.addModule(new StringModule());
            quercus.addModule(new VariableModule());
            quercus.addModule(new MiscModule());
            quercus.addModule(new NetworkModule());
            quercus.addModule(new OptionsModule());
            quercus.addModule(new OutputModule());
            quercus.addModule(new TokenModule());
            quercus.addModule(new UrlModule());
            quercus.addModule(new ExifModule());
            quercus.addModule(new FunctionModule());
            quercus.addModule(new HashModule());
            quercus.addModule(new HtmlModule());
            quercus.addModule(new HttpModule());
            quercus.addModule(new ImageModule());
            quercus.addModule(new JavaModule());
            quercus.addModule(new MathModule());
            quercus.addModule(new MhashModule());
            quercus.addModule(new ApacheModule());
            quercus.addModule(new ApcModule());
            quercus.addModule(new ArrayModule());
            quercus.addModule(new BcmathModule());
            quercus.addModule(new ClassesModule());
            quercus.addModule(new CtypeModule());
            quercus.addModule(new ErrorModule());
            quercus.addModule(new RegexpModule());

            QuercusPage qp = quercus.parse(StringStream.open("<?php phpinfo(); ?>"));
            WriteStream out = new WriteStream(StdoutStream.create());
            Env env = quercus.createEnv(qp, out, null, null);
            env.setTimeLimit(-1);

            Value res = qp.executeTop(env);
            //return new String(out.getBuffer(), out.getBufferOffset());
            //return new String(out.getBuffer());


            //out.seekEnd(out.getBufferSize());
            //System.out.println(directS.get_string());


            out.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


}
