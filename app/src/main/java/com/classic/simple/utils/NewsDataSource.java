package com.classic.simple.utils;

import com.classic.simple.model.News;
import java.util.ArrayList;

public final class NewsDataSource {
    private static final String AUTHOR = "续写经典";
    private static ArrayList<News> newsList;

    //测试数据
    public static ArrayList<News> getNewsList(){
        if(null==newsList || newsList.isEmpty()){
            newsList = new ArrayList<>();
            newsList.add(new News(News.TYPE_SINGLE_PICTURE,AUTHOR,"谷歌返华前兆？桥头堡Google Play降低门槛","北京时间1月26日上午消息，谷歌今日在Play Games中取消了强制使用Google+登录的要求，允许没有谷歌帐号的新用户全面使用这项服务。有相关评论称，此举或意味着谷歌返华又更近了一步。","http://n.sinaimg.cn/tech/transform/20160126/g8H_-fxnvhvu7058461.jpg"));
            newsList.add(new News(News.TYPE_NONE_PICTURE,AUTHOR,"河北工商局：三大运营商涉嫌不正当竞争","记者从河北省工商局获悉，中国移动河北分公司、中国联通河北分公司、中国电信河北分公司等电信运营商采取赠与学校领导、班主任、联系人老师话费的方式，向广大学生强行推销“校讯通”“家校通”“翼校通”等产品，涉嫌违反《中华人民共和国反不正当竞争法》。"));
            newsList.add(new News(News.TYPE_MULTIPLE_PICTURE,AUTHOR,"杭州零下9度 一夜寒风吹冻西湖","1月25日清晨，西湖雷峰塔景区冰封美景醉人，当日，受霸王级寒潮影响，杭州主城区出现最低-9.3℃气温，逼近1969年的历史极值，受此影响，杭州西湖部分水域出现严重结冰现象。","http://www.sinaimg.cn/dy/slidenews/1_img/2016_04/2841_656896_533796.jpg;http://www.sinaimg.cn/dy/slidenews/1_img/2016_04/2841_656904_171122.jpg"));
            newsList.add(new News(News.TYPE_SINGLE_PICTURE,AUTHOR,"创投机构回应上海政府风投补偿：返税意义更大","引力创投是落户上海的早期投资机构，其合伙人戴周颖在接受新浪科技采访时认为，上海市此举还是属于招商引资的一个办法。在此之前，上海市就有专门的政策和方案，通过引导基金的方式，让更多GP(基金管理合伙人)注入上海，并且基金须有一定比例投资上海。","http://n.sinaimg.cn/tech/transform/20160126/KR43-fxnuvxc1994221.jpg"));
            newsList.add(new News(News.TYPE_NONE_PICTURE,AUTHOR,"上海新规：天使投资有损失可获补偿 最高补六成","近日，上海市科学技术委员会、上海市财政局、上海市发改委联合发布《上海市天使投资风险补偿管理暂行办法》，提出对投资机构投资种子期、初创期科技型企业，最终回收的转让收入与退出前累计投入该企业的投资额之间的差额部分，给予以一定比例的财务补偿。"));
            newsList.add(new News(News.TYPE_SINGLE_PICTURE,AUTHOR,"我们为什么投资马斯克的超级高铁Hyperloop","对于天使投资而言，每一次投资就像一次豪赌。当Peter Thiel投资Facebook、Paul Graham投资Airbnb、Sherwin Pishevar投资Uber的时候，都被很多人看作是“疯子”。当然现在有无数的投资人为错过了投资机会懊悔不已，但在最初撒出大笔现金时，谁也说不准它会成长为独角兽，还是让投资人输得血本无归。","http://s.img.mix.sina.com.cn/auto/resize?size=560_0&img=http%3A%2F%2Fsinastorage.com%2Fstorage.csj.sina.com.cn%2F319f277142405b15c3c702b03c8af1cf.jpg"));
            newsList.add(new News(News.TYPE_MULTIPLE_PICTURE,AUTHOR,"2015电子信息制造业销售产值同比增长8.7%","1－12月，规模以上电子信息制造业内销产值同比增长17.3%，出口交货值同比下降0.1%。从内外资角度看，1－12月，内资企业的销售产值同比增长17.8%，港澳台投资企业销售产值同比增长8.3%，外商投资企业销售产值同比下降0.1%。","http://n.sinaimg.cn/tech/transform/20160125/onUR-fxnuvxe8392239.png;http://n.sinaimg.cn/tech/transform/20160125/Ll3P-fxnuvxe8392273.png"));
            newsList.add(new News(News.TYPE_NONE_PICTURE,AUTHOR,"微软反垄断案新突破 Win10或需剥离可信计算","半年多来，我们跟微软进行过数次谈判。我们希望微软可以按照我国法律法规实现本土化，但谈判至今还没有结果。”沈昌祥系此次微软网络安全审查的负责人，他笑称，“每一次都斗争激烈，我们要坚守网络安全主权，而微软也不止是一家商业公司，它的决策也需要通过美国本土的审批。"));
            newsList.add(new News(News.TYPE_NONE_PICTURE,AUTHOR,"高管集体离职致Twitter股价开盘跳水逾6%","新浪科技讯 北京时间1月26日早间消息，在5名重要高管离职后，Twitter股价周一开盘下跌逾6个百分点。此次离职的包括该公司工程副总裁亚历克斯·罗特尔(Alex Roettter)、产品副总裁凯文·维尔(Kevin Weil)、媒体主管凯蒂·斯坦顿(Katie Stanton)、人力资源副总裁布莱恩·施佩尔(Brian Schipper)和Vine主管詹森·托夫(Jason Toff)。"));
            newsList.add(new News(News.TYPE_SINGLE_PICTURE,AUTHOR,"动物弱肉强食惊心动魄捕食：雄狮遭水牛追赶","当地时间2015年2月4日，一头雄狮在一群水牛的追赶下居然也会落荒而逃。虽然被成为是森林之王，但这只明显更像是一只受到惊吓的小猫。一开始两头狮子在草地上休息，突然，水牛发现了敌人的身影，便一同扑上去追赶，狮子吓得立刻掉头逃走了。","http://www.sinaimg.cn/dy/slidenews/5_img/2016_04/453_74575_776168.jpg"));
            newsList.add(new News(News.TYPE_SINGLE_PICTURE,AUTHOR,"非洲变色龙捕食甲虫瞬间：伸出长舌头黏住吞下","摄影师托马斯•惠滕（Thomas Whetten）在纳米比亚西部的纳米布沙漠（Namib Desert）拍摄到一组纳马夸变色龙从后方捕食毫无防备的甲虫的照片。","http://www.sinaimg.cn/dy/slidenews/5_img/2016_04/453_74569_384735.jpg"));
            newsList.add(new News(News.TYPE_SINGLE_PICTURE,AUTHOR,"潜水员与大白鲨亲密接触上演惊魂时刻","近日，在墨西哥瓜达卢佩圣母岛海岸，一名潜水员通过向大白鲨投喂食物将其引诱至笼子边，将他的手臂伸出，轻轻抚摸了大白鲨的鼻子。这一惊魂时刻被摄影师记录了下来。","http://www.sinaimg.cn/dy/slidenews/5_img/2016_04/453_74552_646977.jpg"));
            newsList.add(new News(News.TYPE_MULTIPLE_PICTURE,AUTHOR,"网曝富士康郑州工厂发生火灾 现场黑烟滚滚","火灾发生地点位于新郑富士康02区，当时消防人员已经到位，随后大火被扑灭。目前尚不知道起火原因，人员伤亡情况也未公布，暂不清楚是否会对部分手机数码产品的供货造成影响。目前尚未有富士康方面的公开回应。","http://n.sinaimg.cn/translate/20160125/q0v5-fxnuwcr7470993.jpg;http://n.sinaimg.cn/translate/20160125/jMrS-fxnuwfi3015757.jpg"));
            newsList.add(new News(News.TYPE_MULTIPLE_PICTURE,AUTHOR,"“汪星人”偷偷溜进半程马拉松还混到名次","话说最近在美国阿拉巴马州的一个小镇发生了件神奇的事。Ludivine是只2岁半的猎犬，周六早晨她的主人照例放它出去溜达。万万没想到，在这户人家附近正举行一场半程马拉松，这狗一溜达就跑到了马拉松起点附近，默默和选手们一起","http://www.sinaimg.cn/dy/slidenews/1_img/2016_04/2841_656940_447365.jpg;http://www.sinaimg.cn/dy/slidenews/1_img/2016_04/2841_656941_891976.jpg"));
            newsList.add(new News(News.TYPE_NONE_PICTURE,AUTHOR,"清华控股将收购两家半导体制造公司","北京时间1月26日早间消息，清华控股董事长徐井宏本周在达沃斯世界经济论坛上表示，清华控股计划收购两家半导体制造公司。此举表明，中国计划进一步加强半导体制造技术。投行Stifel Nicolaus分析师阿隆·雷克斯(Aaron Rakers)表示：“获得政府支持的清华控股2016年将投资2000亿元人民币，用于并购活动。”"));
            newsList.add(new News(News.TYPE_NONE_PICTURE,AUTHOR,"马斯克：油价下跌正在冲击电动汽车市场","马斯克周一表示：“我认为，整个行业肯定受到了低油价的冲击。这从经济性上来说是合理的。”近期，油价下跌正在改变美国汽车市场的格局，皮卡和SUV等“油老虎”车型的销量去年出现增长。今年以来，油价的跌势仍在延续，而上周则创下12年以来最低纪录。"));
            newsList.add(new News(News.TYPE_NONE_PICTURE,AUTHOR,"圆通延吉店老板跑路 大量快件滞留","临近春节，又一家快递加盟公司停摆：圆通延吉公司老板被曝拖欠员工工资，疑似跑路，大量快件堆放在网点外，无人管理。去年“双11”至今，不仅是圆通，加盟制快递企业“四通一达”其余的申通、中通、百世汇通和韵达也均被媒体曝出部分加盟网点出现疑似停工、倒闭等情况。伴随着电商的红火，借势而盛的加盟制快递企业，为何在近期接连遭遇“寒潮”？快递企业又该如何加强管理和制度创新，提供更好的服务呢？"));
        }
        return newsList;
    }

}
