package me.ryandowling.allmightytwitchtoolbox.data.streamtip;


import java.util.List;

public class StreamTipTips {
    private int _count;
    private int _limit;
    private int _offset;
    private int status;
    private List<StreamTipTip> tips;

    public List<StreamTipTip> getTips() {
        return this.tips;
    }
}
