package com.lucasmoraist.news_letter_ai.infrastructure.schedule;

import com.lucasmoraist.news_letter_ai.application.usecases.notice.GenerateNoticeCase;
import com.lucasmoraist.news_letter_ai.application.usecases.notification.SendNotification;
import com.lucasmoraist.news_letter_ai.domain.exceptions.ReadValueException;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DailyScheduleTest {

    @Mock
    GenerateNoticeCase generateNoticeCase;
    @Mock
    SendNotification sendNotification;
    @InjectMocks
    DailySchedule dailySchedule;

    @Test
    @DisplayName("Should parse generated notices and call send notification")
    void case01() {
        when(generateNoticeCase.execute()).thenReturn("```json\n[{\"title\":\"exemplo\"}]\n```");

        dailySchedule.execute();

        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(sendNotification).execute(captor.capture());

        List<?> notices = captor.getValue();
        assertEquals(1, notices.size());

        Object first = notices.get(0);
        assertInstanceOf(Notice.class, first);
        assertEquals("exemplo", ReflectionTestUtils.getField(first, "title"));
    }

    @Test
    @DisplayName("Should throw ReadValueException when generated content is invalid")
    void case02() {
        when(generateNoticeCase.execute()).thenReturn("invalid-json");

        assertThrows(ReadValueException.class, dailySchedule::execute);

        verifyNoInteractions(sendNotification);
    }
}
