package pl.jakubchmura.iorparser;

import org.jacorb.orb.ParsedIOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.jacorb.orb.util.PrintIOR.printIOR;

@Controller
public class IorParserController {

    private final static Logger logger = LoggerFactory.getLogger(IorParserController.class);

    @PostMapping("/")
    public String parseIor(@ModelAttribute Ior ior, Model model) {
        String parsedIor = null;
        try {
            parsedIor = parseIor(ior.getIor());
        } catch (Exception e) {
            logger.warn("IOR {} could not be parsed", ior.getIor(), e);
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("parsed", parsedIor);
        return "index";
    }

    @GetMapping("/")
    public String iorForm(Model model) {
        model.addAttribute("ior", new Ior());
        return "index";
    }

    private String parseIor(String iorString) {
        final org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init((String[]) null, null);
        final org.jacorb.orb.ORB jorb = (org.jacorb.orb.ORB) orb;

        final ParsedIOR pior = new ParsedIOR(jorb, iorString);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);


        printIOR(jorb, pior, pw);
        return sw.toString();
    }

}
