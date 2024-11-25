package com.chawoomi.outbound.adapter.service.dto;

import java.util.List;

public class ReviewSummaryDto {
    private Double totalRate; // 총합 합계
    private List<ReviewDetailDto> reviews; // 리뷰 리스트

    // 생성자
    public ReviewSummaryDto(Double totalRate, List<ReviewDetailDto> reviews) {
        this.totalRate = totalRate;
        this.reviews = reviews;
    }

    // Getter & Setter
    public Double getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Double totalRate) {
        this.totalRate = totalRate;
    }

    public List<ReviewDetailDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDetailDto> reviews) {
        this.reviews = reviews;
    }

    // 내부 클래스 또는 별도 파일로 정의 가능
    public static class ReviewDetailDto {
        private String content; // 리뷰 내용
        private Integer rate;   // 리뷰 점수

        // 생성자
        public ReviewDetailDto(String content, Integer rate) {
            this.content = content;
            this.rate = rate;
        }

        // Getter & Setter
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
            this.rate = rate;
        }
    }
}
