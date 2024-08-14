package alex_pr.wallet_app.controller;

import alex_pr.wallet_app.entity.WalletEntity;
import alex_pr.wallet_app.service.WalletService;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @MockBean
    private Bucket bucket;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new WalletController(walletService, bucket)).build();
    }

    @Test
    void testCreateWallet_Success() throws Exception {
        WalletEntity wallet = new WalletEntity();
        wallet.setId(1);
        wallet.setAmount(100);

        when(bucket.tryConsume(1)).thenReturn(true);
        when(walletService.createWallet()).thenReturn(wallet);

        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"amount\":100}"));
    }

    @Test
    public void testCreateWallet_RateLimitExceeded() throws Exception {
        when(bucket.tryConsume(1)).thenReturn(false);

        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    public void testDeposit_Success() throws Exception {
        when(bucket.tryConsume(1)).thenReturn(true);
        when(walletService.deposit(anyInt(), anyInt())).thenReturn(200);

        mockMvc.perform(post("/api/v1/deposit/1")
                        .param("amount", "200")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string("200"));
    }
    @Test
    public void testDeposit_RateLimitExceeded() throws Exception {
        when(bucket.tryConsume(1)).thenReturn(false);

        mockMvc.perform(post("/api/v1/deposit/1")
                        .param("amount", "200")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    public void testWithdraw_Success() throws Exception {
        when(bucket.tryConsume(1)).thenReturn(true);
        when(walletService.withdraw(anyInt(), anyInt())).thenReturn(50);

        mockMvc.perform(post("/api/v1/withdraw/1")
                        .param("amount", "50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string("50"));
    }

    @Test
    public void testWithdraw_RateLimitExceeded() throws Exception {
        when(bucket.tryConsume(1)).thenReturn(false);

        mockMvc.perform(post("/api/v1/withdraw/1")
                        .param("amount", "50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    public void testGetBalance_Failure() throws Exception {
        Mockito.when(bucket.tryConsume(1)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isTooManyRequests());
    }
    
    @Test
    public void testGetBalance_Exception() throws Exception {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
        Mockito.when(walletService.balance(1)).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}