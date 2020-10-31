package com.newsinfo.service.implementation;

import com.newsinfo.entity.EndorserProfile;
import com.newsinfo.entity.PolledEndorsedNews;
import com.newsinfo.initializer.InitializerApplication;
import com.newsinfo.model.endorser.RegisterRequest;
import com.newsinfo.repository.EndorserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = InitializerApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class EndorserProfileServiceImplTest {

    String testEndorserId;
    @Autowired
    private EndorserProfileServiceImpl endorserProfileService;
    @Autowired
    private EndorserProfileRepository endorserProfileRepository;

    @BeforeEach
    public void testRegister() {
        RegisterRequest testRegister = new RegisterRequest();
        testRegister.setFirstName("FirstX");
        testRegister.setLastName("LastX");
        testRegister.setLocation("CoimbatoreX");
        testEndorserId = endorserProfileService.registerEndorser(testRegister);
    }

    @Test
    public void testRegisterEndorser() {
        assertNotNull(testEndorserId);
        assertNotNull(endorserProfileRepository.findById(testEndorserId));
    }

    @Test
    public void testUpdateProfileForNewsPolled() {
        String testNewsId = "1";
        endorserProfileService.updateProfileForNewsPolled(testEndorserId, testNewsId);
        Optional<EndorserProfile> testEndorser = endorserProfileRepository.findById(testEndorserId);

        assertTrue(testEndorser.isPresent());
        assertTrue(testEndorser.get().getPolledEndorsedNewsSet().size() > 0);
        PolledEndorsedNews testPolledEndorsedNews = testEndorser.get().getPolledEndorsedNewsSet().iterator().next();
        assertEquals(testEndorser.get(), testPolledEndorsedNews.getEndorserProfile());
        assertEquals(testNewsId, testPolledEndorsedNews.getNewsId());
    }

    @Test
    public void testUpdateProfileForNewsPolledNotExistProfile() {
        String testNewsId = "1";
        testEndorserId = "NEN_12345";
        assertThrows(EntityNotFoundException.class,
                () -> endorserProfileService.updateProfileForNewsPolled(testEndorserId, testNewsId));
    }

}