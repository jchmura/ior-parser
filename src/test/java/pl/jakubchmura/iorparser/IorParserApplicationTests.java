package pl.jakubchmura.iorparser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IorParserApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("IOR Parser")))
                .andExpect(model().attribute("ior", equalTo(new Ior())))
                .andExpect(view().name("index"));
    }

    @Test
    public void testParsing() throws Exception {
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("ior", "IOR:010400002800000049444c3a6f6d672e6f72672f436f734e616d696e672f4e616d696e67436f6e746578743a312e300001000000000000002c0000000101000014000000746e696e6b7061642e74656c656e742e6e6574004f060000080000000000000001000000"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("IOR Parser")))
                .andExpect(model().attribute("parsed", containsString("tninkpad.telent.net")));
    }

    @Test
    public void testParsingWithWhitespace() throws Exception {
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("ior", "IOR:010400002800000049444c3a6f6d672e6f72672f436f734e616d696 " +
                        "e672f4e616d696e67436f6e746578743a312e300001000000000000002c0000000101000014000000746e696e6b" +
                        "\n" +
                        "7061642e74656c656e742e6e6574004f060000080000000000000001000000"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("IOR Parser")))
                .andExpect(model().attribute("parsed", containsString("tninkpad.telent.net")));
    }

}
