package com.bernhard.jobapp.review.impl;

import com.bernhard.jobapp.company.Company;
import com.bernhard.jobapp.company.CompanyService;
import com.bernhard.jobapp.review.Review;
import com.bernhard.jobapp.review.ReviewRepository;
import com.bernhard.jobapp.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired})
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews
                .stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            updatedReview.setCompany(company);
            updatedReview.setId(reviewId);
            reviewRepository.save(updatedReview);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        Company company = companyService.getCompanyById(companyId);
        boolean reviewIsExisted = reviewRepository.existsById(reviewId);
        if (company != null && reviewIsExisted) {
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company currentCompany = review.getCompany();
            currentCompany.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(companyId, currentCompany);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
