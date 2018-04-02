package com.graduation.service;


import com.graduation.domain.view.IndexCollectorView;
import com.graduation.repository.CollectorRepository;
import com.graduation.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @Description: 获取收藏家
 * @Auth: JiangL
 * @Date: 2018/3/19 14:14
 * @Version: 1.0
 **/
@Service
public class CollectorService {
    private CollectorRepository collectorRepository;

    private UserRepository userRepository;

    public CollectorService(CollectorRepository collectorRepository, UserRepository userRepository) {
        this.collectorRepository = collectorRepository;
        this.userRepository = userRepository;
    }

    /**
     * 获取收藏家
     */
    public IndexCollectorView getCollectors() {
        IndexCollectorView indexCollectorView = new IndexCollectorView();
        try {
            Long mostCollectUser = collectorRepository.getMostCollectUser();
            indexCollectorView.setMostCollectUser(userRepository.findById(mostCollectUser));
            Long mostFollowedUser = collectorRepository.getMostFollowedUser(mostCollectUser);
            indexCollectorView.setMostFollowedUser(userRepository.findById(mostFollowedUser));
            String notUserIds = mostCollectUser + "," + mostFollowedUser;
            Long mostPraisedUser = collectorRepository.getMostPraisedUser(notUserIds);
            indexCollectorView.setMostPraisedUser(userRepository.findById(mostPraisedUser));
            notUserIds += "," + mostPraisedUser;
            Long mostCommentedUser = collectorRepository.getMostCommentedUser(notUserIds);
            indexCollectorView.setMostCommentedUser(userRepository.findById(mostCommentedUser));
            notUserIds += "," + mostCommentedUser;
            Long mostPopularUser = collectorRepository.getMostPopularUser(notUserIds);
            indexCollectorView.setMostPopularUser(userRepository.findById(mostPopularUser));
            notUserIds += "," + mostPopularUser;
            Long mostActiveUser = collectorRepository.getMostActiveUser(notUserIds);
            indexCollectorView.setMostActiveUser(userRepository.findById(mostActiveUser));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return indexCollectorView;
    }
}
