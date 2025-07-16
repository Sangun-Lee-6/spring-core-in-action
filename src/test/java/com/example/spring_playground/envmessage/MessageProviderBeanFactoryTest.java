package com.example.spring_playground.envmessage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MessageProviderBeanFactoryTest {

    @Test
    @DisplayName("BeanFactory는 @Profile을 인식하지 못하므로 예외 발생")
    void beanFactoryTest_profile() {
        // BeanFactory + 수동 빈 등록
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);

        // @Profile 붙은 두 클래스를 직접 등록 시도
        reader.register(DevMessageProvider.class);
        reader.register(ProdMessageProvider.class);

        // NoSuchBeanDefinitionException 발생
        assertThatThrownBy(() -> beanFactory.getBean(MessageProvider.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Test
    @DisplayName("ApplicationContext는 @Profile을 인식 -> dev/prod 환경 인식")
    void applicationContext_profile() {
        // 스프링 컨텍스트 생성
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 프로파일을 명시적으로 dev로 설정
        ConfigurableEnvironment environment = context.getEnvironment();
        environment.setActiveProfiles("dev");

        // 클래스 수동 등록
        context.register(DevMessageProvider.class, ProdMessageProvider.class);
        context.refresh(); // 빈 등록 및 초기화

        // 빈 조회
        MessageProvider provider = context.getBean(MessageProvider.class);

        // DevMessageProvider가 주입되었는지 검증
        Assertions.assertThat(provider).isInstanceOf(DevMessageProvider.class);
        Assertions.assertThat(provider.getMessage()).isEqualTo("[DEV] 개발 환경입니다.");

        // ProdMessageProvider인지 확인 -> 예외 발생
        assertThatThrownBy(() -> {
            Assertions.assertThat(provider)
                    .isInstanceOf(ProdMessageProvider.class);
        }).isInstanceOf(AssertionError.class)
                .hasMessageContaining("to be an instance of");
    }
}

