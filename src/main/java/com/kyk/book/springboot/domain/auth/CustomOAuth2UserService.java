package com.kyk.book.springboot.domain.auth;

import com.kyk.book.springboot.domain.auth.dto.OAuthAttributes;
import com.kyk.book.springboot.domain.auth.dto.SessionUser;
import com.kyk.book.springboot.domain.user.User;
import com.kyk.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(kakao, google, naver)에서 가져온 유저 정보를 담고있음

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // ex) 구글인지 네이버 로그인 연동 시에 서로 구분하기 위함
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값을 이야기한다. PK와 같은 의미
        // 구글의 경우 기본적으로 코드(="sub")를 지원하지만 네이버, 카카오 등은 기본 지원하지 않는다.
        // 즉, 네이버 로그인과 구글 로그인을 동시 지원할 때 사용된다.
        String userNameAttributeName = userRequest.getClientRegistration()
                                                  .getProviderDetails()
                                                  .getUserInfoEndpoint()
                                                  .getUserNameAttributeName();

        // OAuthAttributes은 OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // SessionUser은 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        // 왜 User클래스를 쓰지 않고 새로 만들어서 쓰는가? -> User 클래스에서 직렬화하지 않았기 때문이다.
        // 그럼 직렬화하면 가능한가? -> User 클래스는 엔티티인데 언제 다른 엔티티와 관계가 형성될지 모르기에 매핑된다면 직렬화한 대상에 자식들까지 포함되어 성능 이슈, 부수 효과가 발생한다.
        // 그래서 직렬화 기능을 가진 세션 Dto를 하나 추가로 만드는 것이 운영/유지보수에 많은 도움이 된다.
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
