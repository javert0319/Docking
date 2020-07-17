package com.lib.network.bean.entry.music;

import java.util.List;

/**
 * @ClassName: MusicMiguHomeBean
 * @Description: 咪咕首页信息查询
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class MusicMiguHomeBean {

    /**
     * name : 全部
     * type : songCata
     * refrenceId : 1033493665739075584
     * infos : [{"singer":"马頔","name":"漂(电视剧《梦在海这边》主题曲)","id":"63388708692","dir":"http://open.cn/l/2019/11/13/b2facf82646a4022a3d88dd1a817a90f.jpg?m"}]
     */

    private String name;
    private String type;
    private long refrenceId;
    private List<InfosBean> infos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRefrenceId() {
        return refrenceId;
    }

    public void setRefrenceId(long refrenceId) {
        this.refrenceId = refrenceId;
    }

    public List<InfosBean> getInfos() {
        return infos;
    }

    public void setInfos(List<InfosBean> infos) {
        this.infos = infos;
    }

    public static class InfosBean {
        /**
         * singer : 马頔
         * name : 漂(电视剧《梦在海这边》主题曲)
         * id : 63388708692
         * dir : http://open.cn/l/2019/11/13/b2facf82646a4022a3d88dd1a817a90f.jpg?m
         */

        private String singer;
        private String name;
        private String id;
        private String dir;
        private Long refrenceId;

        public Long getRefrenceId() {
            return refrenceId;
        }

        public void setRefrenceId(Long refrenceId) {
            this.refrenceId = refrenceId;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }
}
