package service;

import model.Chapter;
import repository.ChapterRepository;

import java.sql.SQLException;

public class ChapterService {
    public static Chapter getNextApprovedChapter(int chapterID){
        try {
            Chapter nextChapter = ChapterRepository.getInstance().getNextChapter(chapterID);
            if (nextChapter != null)
                while (nextChapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_PENDING) || nextChapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_REJECTED)) {
                    nextChapter = ChapterRepository.getInstance().getNextChapter(nextChapter.getId());
                    if (nextChapter == null) break;
                }
            return nextChapter;
        }
        catch (SQLException e){
            return null;
        }
    }
    public static Chapter getPreviousApprovedChapter(int chapterID){
        try {
            Chapter previousChapter = ChapterRepository.getInstance().getPreviousChapter(chapterID);
            if (previousChapter != null)
                while (previousChapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_PENDING) || previousChapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_REJECTED)) {
                    previousChapter = ChapterRepository.getInstance().getPreviousChapter(previousChapter.getId());
                    if (previousChapter == null) break;
                }
            if(ChapterRepository.getInstance().isVirtualChapter(previousChapter)){
                return null;
            }
            return previousChapter;
        }
        catch (SQLException e){
            return null;
        }
    }
}
