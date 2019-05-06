package lee.vshare.audiotalkie.audio;

/**
 * CreateDate：18-11-7 on 下午3:02
 * Describe:
 * Coder: lee
 */
public interface BaseAudio {

    void init();

    void begin(String voiceIdentification);

    void over();

    void release();

}
