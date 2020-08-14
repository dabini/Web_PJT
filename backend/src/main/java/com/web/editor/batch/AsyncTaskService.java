package com.web.editor.batch;

import com.web.editor.model.service.request.RequestService;
import com.web.editor.model.service.user.search.SearchService;
import com.web.editor.model.service.user.search.redis.SearchRedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AsyncTaskService {
    
	@Autowired
	SearchRedisService searchRedisService;

	@Autowired
	SearchService searchService;

	@Autowired
    RequestService requestService;
    
    /*
     * 해당 파일에서 백그라운드에서 실행할 로직을 구현한다.
     */
	// 초 * 1000
	// 1시간마다 갱신으로 설정
	@Scheduled(fixedDelay = 86400000)
	public void SaveDatatoRedis() {
        System.out.println("Thread Start");
        
		// long res = searchRedisService.deleteAll();
		// System.out.println("지워진수>>>>"+res);
		
		// DB내용 Redis로 끌어올 필요 있음. 
		searchRedisService.portfolioAndBookmarkSave(searchService.joinBookmarks());
		searchRedisService.portfolioAndVideoSave(searchService.joinVideos());
		searchRedisService.requestAndReviewSave(requestService.joinScores());
		searchRedisService.makeUserInfo();
		searchRedisService.searchRequestVideoInfoSave(requestService.searchRequestVideoInfo());
        searchRedisService.portfolioTagSave(searchService.searchPortfolioTag());
        
		System.out.println("Thread End");
	}
}