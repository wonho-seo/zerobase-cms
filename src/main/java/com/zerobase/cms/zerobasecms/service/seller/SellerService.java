package com.zerobase.cms.zerobasecms.service.seller;

import static com.zerobase.cms.zerobasecms.exception.ErrorCode.ALREADY_VERIFY;
import static com.zerobase.cms.zerobasecms.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.cms.zerobasecms.domain.PostSignUp.PostSignUpRequest;
import com.zerobase.cms.zerobasecms.domain.model.Seller;
import com.zerobase.cms.zerobasecms.domain.repository.SellerRepository;
import com.zerobase.cms.zerobasecms.exception.CustomException;
import com.zerobase.cms.zerobasecms.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public Seller findByIdAndEmail(Long id, String email){
        return sellerRepository.findByIdAndEmail(id, email)
            .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public Seller findValidSeller(String email, String password){
        return sellerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password)
            .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public Seller signUp(PostSignUpRequest form){
        return sellerRepository.save(Seller.form(form));
    }

    public boolean isEmailExist(String email) {
        return sellerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();

    }

    @Transactional
    public void verifyEmail(String email, String code){
        Seller seller = sellerRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if(seller.isVerify()){
            throw new CustomException(ALREADY_VERIFY);
        } else if (!seller.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        seller.setVerify(true);
    }

    @Transactional
    public LocalDateTime ChangeSellerValidateEmail(Long sellerId, String verificationCode) {
        Seller seller = sellerRepository.findById(sellerId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        seller.setVerificationCode(verificationCode);
        seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));

        return seller.getVerifyExpiredAt();
    }
}
