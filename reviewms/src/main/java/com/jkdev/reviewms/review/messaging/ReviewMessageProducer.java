package com.jkdev.reviewms.review.messaging;

import com.jkdev.reviewms.review.Review;
import com.jkdev.reviewms.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    // Declares a RabbitTemplate instance that will be used to interact with RabbitMQ
    private final RabbitTemplate rabbitTemplate;

    // Constructor to initialize the rabbitTemplate dependency
    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate; // Dependency injection ensures RabbitTemplate is ready for use
    }

    // Sends a review message to a specific RabbitMQ queue
    public void sendMessage(Review review) {
        ReviewMessage reviewMessage = new ReviewMessage(); // Creates a new instance of ReviewMessage to transfer review data

        // Copies data from the Review object to the ReviewMessage object
        reviewMessage.setTitle(review.getTitle());
        reviewMessage.setId(review.getId());
        reviewMessage.setCompanyId(review.getCompanyId());
        reviewMessage.setDescription(review.getDescription());
        reviewMessage.setRating(review.getRating());

        // Sends the ReviewMessage object to the RabbitMQ queue named 'companyRatingQueue'
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
    }
}
