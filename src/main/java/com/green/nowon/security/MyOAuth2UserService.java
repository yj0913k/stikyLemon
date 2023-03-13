package com.green.nowon.security;

import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.MemberEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Log4j2
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    MemberEntityRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        /*      //소셜 로그인 시 값들이 무엇이 어떻게오는지 확인하는 주석구간.
        System.out.println(">>>> getName() : "+oAuth2User.getName());;

        oAuth2User.getAuthorities().forEach(role->{
            System.out.println(">>>>role : "+role);
        });
*/
        Map<String, Object> attributes = oAuth2User.getAttributes();
        /*for(String key : attributes.keySet()){
            System.out.println(">>>> "+key+" : "+attributes.get(key));//key,value
        }*/


        /*
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        for(String key:response.keySet()){
            System.out.println("response: "+key +" : "+response.get(key));
        }
        */
        //google, naver, kakao
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("registrationId: "+registrationId);

        //소셜세팅한 객체를 생성
        MyUserDetails myUserDetails= setMyUserDetails(attributes,oAuth2User,registrationId);


        log.info("memberEntityaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        //멤버엔티티를 설정
        MemberEntity memberEntity;
        Optional<MemberEntity> result = repository.findByEmailAndSocialAndDeleted(myUserDetails.getEmail(),myUserDetails.isSocial(), false);
        //이미 존재하면 받아오고
        if(result.isPresent()){
            memberEntity=result.get();
        }else{  //존재하지 않으면 새로 생성
            memberEntity = MemberEntity.builder()
                    .email(myUserDetails.getEmail())
                    .name(myUserDetails.getName())
                    .nickName(myUserDetails.getNickName())
                    .pass(encoder.encode("1111"))   //소셜은 비밀번호 의미없지만 디비엔필요
                    .social(myUserDetails.isSocial())
                    .build().addRole(MyRole.USER);
            repository.save(memberEntity);
        }
        log.info(memberEntity);

        myUserDetails.setMno(memberEntity.getMno());



        return myUserDetails;
    }

    private MyUserDetails setMyUserDetails(Map<String, Object> attributes, OAuth2User oAuth2User, String registrationId) {

        Set<SimpleGrantedAuthority> authorities=null;
        //private String nameAttributeKey;
        String nickName=null;
        String email=null;
        //private boolean social;
        String profileImg=null;

        for(String key : attributes.keySet()){
            System.out.println(">>>> "+key+" : "+attributes.get(key));//key,value
        }

        authorities = (Set<SimpleGrantedAuthority>) oAuth2User.getAuthorities();
        //this.social=true;
        //소셜별로 데이터 양식이 조금 다르기 때문에 다른부분만 다르게 세팅
        if (registrationId.equals("google")) {
            attributes = oAuth2User.getAttributes();
            email = (String) attributes.get("email");
            nickName = (String) attributes.get("name");
            profileImg = (String) attributes.get("picture");
        } else if (registrationId.equals("naver")) {
            attributes = oAuth2User.getAttribute("response");    //: 정보들이 response 안에 있음!
            email = (String) attributes.get("email");
            nickName = (String) attributes.get("name");
            profileImg = (String) attributes.get("profile_image");
        } else if (registrationId.equals("kakao")) {
            attributes = oAuth2User.getAttribute("kakao_account");    //: 정보들이 response 안에 있음!
            email = (String) attributes.get("email");
            nickName = ((Map<String, String>) attributes.get("profile")).get("nickname");
            profileImg = null;   //개발자센터 동의항목에서 체크 안한듯?
        }

        return new MyUserDetails(email, encoder.encode("1111"), authorities ,nickName);
    }

}
