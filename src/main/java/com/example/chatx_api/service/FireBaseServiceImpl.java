package com.example.chatx_api.service;

import com.example.chatx_api.dto.MemberDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FireBaseServiceImpl implements FireBaseService{

    private final MemberService memberService;
    private final CookieService cookieService;

    @Override
    public ResponseEntity<Void> verifyFbToken(HttpServletResponse response, String idToken)  {

        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            MemberDto memberDto = MemberDto.builder()
                    .memberEmail(firebaseToken.getEmail())
                    .build();

            Long memberId = memberService.getMemberId(memberDto);

            if (memberId == null) {
                memberId = memberService.saveMember(memberDto);
            }

            cookieService.setCookie(memberId.toString(), response);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (FirebaseAuthException firebaseAuthException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
