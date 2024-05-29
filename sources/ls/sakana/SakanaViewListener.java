package ls.sakana;

public interface SakanaViewListener {
    void skFinished(SakanaView sakanaView);

    void skOnPause(SakanaView sakanaView);

    void skOnResume(SakanaView sakanaView);
}
