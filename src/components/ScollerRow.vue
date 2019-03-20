<template>  
  <div class="scoller-row">
    <scroller scroll-direction="horizontal" class="scroller">
      <div class="panel" v-for="item in list" :key="item.num_iid">
        <image class="item-photo" :src="item.pict_url" resize="cover"></image>
        <text class="item-title">{{item.title}}</text>
        <div class="price">          
          <text class="zk-price">￥{{item.zk_final_price}}</text>
          <text class="reserve-price">￥{{item.reserve_price}}</text>
        </div> 
      </div>
    </scroller>
  </div>
</template>

<script>
  
	export default {
		data() {
      return {
        list: [
          {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=529348904068",
            nick: "洋森之家",
            num_iid: 529348904068,
            pict_url: "https://img.alicdn.com/tfscom/i1/2736782442/TB2AWfralHH8KJjy0FbXXcqlpXa_!!2736782442.jpg",
            provcity: "河北 石家庄",
            reserve_price: "39.80",
            seller_id: 2736782442,
            shop_title: "柳润母婴生活馆",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i2/2736782442/TB2YtRTkk.HL1JjSZFuXXX8dXXa_!!2736782442.jpg",
            "https://img.alicdn.com/tfscom/i1/2736782442/TB2kUNdaesAV1JjSZFsXXadZXXa_!!2736782442.jpg",
            "https://img.alicdn.com/tfscom/i2/2736782442/TB2jlfhX.RIWKJjSZFgXXboxXXa_!!2736782442.jpg",
            "https://img.alicdn.com/tfscom/i1/2736782442/TB2tfmpmGmWQ1JjSZPhXXXCJFXa_!!2736782442.jpg"
            ]
            },
            status: 1,
            title: "超大老人防水纯棉防漏透气隔尿垫成人老年人护理垫床垫可洗尿不湿",
            tk_rate: "1.50",
            type: 1,
            user_type: 0,
            volume: 71,
            zk_final_price: "19.90",
            zk_final_price_wap: "19.90"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=582963602222",
            nick: "松阳振晖",
            num_iid: 582963602222,
            pict_url: "https://img.alicdn.com/tfscom/i1/17197599/O1CN018etsq1260OBTKCA6w_!!0-item_pic.jpg",
            provcity: "浙江 杭州",
            reserve_price: "1680.00",
            seller_id: 17197599,
            shop_title: "唯你原创女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i1/17197599/O1CN01fgKCyT260OBT9N0pJ_!!17197599.jpg",
            "https://img.alicdn.com/tfscom/i3/17197599/O1CN01ugVuDd260OBRTOsgw_!!17197599.jpg",
            "https://img.alicdn.com/tfscom/i2/17197599/O1CN01qdpNox260OBROTLf0_!!17197599.jpg",
            "https://img.alicdn.com/tfscom/i1/17197599/O1CN0115dFAt260OBaQBCRt_!!17197599.jpg"
            ]
            },
            status: 1,
            title: "2018冬季新款女装中长款欧货时尚宽松显瘦加厚面包亮面羽绒服外套",
            tk_rate: "4.50",
            type: 1,
            user_type: 0,
            volume: 4268,
            zk_final_price: "398.00",
            zk_final_price_wap: "398.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=584205077923",
            nick: "雯雯的便便hz",
            num_iid: 584205077923,
            pict_url: "https://img.alicdn.com/tfscom/i2/78403414/O1CN01uRfSOM1b5eejyHgle_!!78403414.jpg",
            provcity: "浙江 杭州",
            reserve_price: "312.00",
            seller_id: 78403414,
            shop_title: "小蚊子 女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i4/78403414/O1CN01AzNw881b5eege3Afh_!!78403414.jpg",
            "https://img.alicdn.com/tfscom/i1/78403414/O1CN013U4igi1b5eege0UD9_!!78403414.jpg",
            "https://img.alicdn.com/tfscom/i4/78403414/O1CN01EpsCLO1b5eehH2E8R_!!78403414.jpg",
            "https://img.alicdn.com/tfscom/i3/78403414/O1CN0165l4NN1b5eeZSgFlg_!!78403414.jpg"
            ]
            },
            status: 1,
            title: "亮面棉衣女装冬季2018新款假两件棉袄中长款加厚连帽羽绒棉服外套",
            tk_rate: "1.80",
            type: 1,
            user_type: 0,
            volume: 1628,
            zk_final_price: "249.90",
            zk_final_price_wap: "249.90"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=583259454597",
            nick: "晓得111",
            num_iid: 583259454597,
            pict_url: "https://img.alicdn.com/tfscom/i1/1016012691/O1CN01CpD3uj1VkWIRPFfOI_!!1016012691.jpg",
            provcity: "浙江 杭州",
            reserve_price: "899.00",
            seller_id: 1016012691,
            shop_title: "MC韩国时尚女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i1/1016012691/O1CN01Meatpm1VkWIRVkbps_!!1016012691.jpg",
            "https://img.alicdn.com/tfscom/i2/1016012691/O1CN01cBEGNn1VkWINvycjb_!!1016012691.jpg",
            "https://img.alicdn.com/tfscom/i2/1016012691/O1CN01uwtdEi1VkWIQZqf0q_!!1016012691.jpg",
            "https://img.alicdn.com/tfscom/i2/1016012691/O1CN01I6F53s1VkWIJmROe4_!!1016012691.jpg"
            ]
            },
            status: 1,
            title: "2018新款女装冬中长款仿羊羔毛羊剪绒外套女格子拼接撞色毛毛大衣",
            tk_rate: "9.00",
            type: 1,
            user_type: 0,
            volume: 9358,
            zk_final_price: "388.00",
            zk_final_price_wap: "388.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=582277199763",
            nick: "周末与白天",
            num_iid: 582277199763,
            pict_url: "https://img.alicdn.com/tfscom/i2/772616220/TB2r9b.ggnH8KJjSspcXXb3QFXa_!!772616220.jpg",
            provcity: "浙江 杭州",
            reserve_price: "1288.00",
            seller_id: 772616220,
            shop_title: "赵DD STUDIO",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i3/772616220/TB2p8x0ktbJ8KJjy1zjXXaqapXa_!!772616220.jpg",
            "https://img.alicdn.com/tfscom/i3/772616220/O1CN01oNgmNW1voo0xqiQ2r_!!772616220.png",
            "https://img.alicdn.com/tfscom/i4/772616220/TB2VylqjY_I8KJjy1XaXXbsxpXa_!!772616220.png",
            "https://img.alicdn.com/tfscom/i1/772616220/O1CN01P5PJy11voo0qo5FQX_!!772616220.jpg"
            ]
            },
            status: 1,
            title: "白鸭绒长款过膝白色羽绒服女2018冬季新款韩版大毛领气质加厚女装",
            tk_rate: "2.40",
            type: 4,
            user_type: 0,
            volume: 1383,
            zk_final_price: "653.50",
            zk_final_price_wap: "653.50"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=581105112160",
            nick: "yxphy123001",
            num_iid: 581105112160,
            pict_url: "https://img.alicdn.com/tfscom/i1/131950134/O1CN011CrPXuUaGe14ZWW_!!131950134.jpg",
            provcity: "浙江 杭州",
            reserve_price: "998.00",
            seller_id: 131950134,
            shop_title: "ds313领袖丽人",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i4/131950134/O1CN011CrPXrRhdldT3Ut_!!131950134.jpg",
            "https://img.alicdn.com/tfscom/i3/131950134/O1CN01oMaiiR1CrPYJ2W2q4_!!131950134.jpg",
            "https://img.alicdn.com/tfscom/i4/131950134/O1CN01bIwbtU1CrPYoN1SYL_!!131950134.jpg",
            "https://img.alicdn.com/tfscom/i3/131950134/O1CN01R5iCol1CrPXnCCovH_!!131950134.jpg"
            ]
            },
            status: 1,
            title: "毛呢外套女中长款2018秋冬季新款流行女装韩版茧型显瘦呢子大衣潮",
            tk_rate: "3.00",
            type: 1,
            user_type: 0,
            volume: 3679,
            zk_final_price: "198.00",
            zk_final_price_wap: "198.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=575570459742",
            nick: "云思木想旗舰店",
            num_iid: 575570459742,
            pict_url: "https://img.alicdn.com/tfscom/i2/1765328414/O1CN01mFeWvM2C1eycaNGgp_!!0-item_pic.jpg",
            provcity: "广东 广州",
            reserve_price: "494.00",
            seller_id: 1765328414,
            shop_title: "云思木想旗舰店",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i2/1765328414/TB2BdYGoVooBKNjSZPhXXc2CXXa_!!1765328414.jpg",
            "https://img.alicdn.com/tfscom/i3/1765328414/TB2JwKzpnqWBKNjSZFxXXcpLpXa_!!1765328414.jpg",
            "https://img.alicdn.com/tfscom/i2/1765328414/TB2p0N_pXkoBKNjSZFkXXb4tFXa_!!1765328414.jpg",
            "https://img.alicdn.com/tfscom/i3/1765328414/TB2_t.lo0cnBKNjSZR0XXcFqFXa_!!1765328414.jpg"
            ]
            },
            status: 1,
            title: "云思木想2018秋装新款女装凤凰印花荷叶袖套头立领卫衣女75815",
            tk_rate: "4.50",
            type: 1,
            user_type: 1,
            volume: 742,
            zk_final_price: "241.00",
            zk_final_price_wap: "241.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=583573811796",
            nick: "点名时间到了",
            num_iid: 583573811796,
            pict_url: "https://img.alicdn.com/tfscom/i3/2107328912/O1CN01lWFdSx2FhkDjzQnVk_!!2107328912.jpg",
            provcity: "浙江 杭州",
            reserve_price: "888.00",
            seller_id: 2107328912,
            shop_title: "DmTime DM家潮流女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i4/2107328912/O1CN01z4nVl72FhkEYruQYL_!!2107328912.jpg",
            "https://img.alicdn.com/tfscom/i4/2107328912/O1CN01nQxmfG2FhkEa46l7T_!!2107328912.jpg",
            "https://img.alicdn.com/tfscom/i3/2107328912/O1CN016s0AiK2FhkDlGPRfy_!!2107328912.jpg",
            "https://img.alicdn.com/tfscom/i2/2107328912/O1CN01VVHKDx2FhkDkG19lA_!!2107328912.jpg"
            ]
            },
            status: 1,
            title: "2018秋冬新款女装韩版毛衣连衣裙针织毛衫长裙包臀裙内搭裙子冬季",
            tk_rate: "9.00",
            type: 1,
            user_type: 0,
            volume: 4407,
            zk_final_price: "178.00",
            zk_final_price_wap: "178.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=582956610447",
            nick: "小六子女装",
            num_iid: 582956610447,
            pict_url: "https://img.alicdn.com/tfscom/i2/2274485118/O1CN01LyEGll1ng5XmfQOqW_!!2274485118.jpg",
            provcity: "广东 广州",
            reserve_price: "198.00",
            seller_id: 2274485118,
            shop_title: "小太阳大码衣舍",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i4/2274485118/O1CN01NjyEfm1ng5XpygqOX_!!2274485118.jpg",
            "https://img.alicdn.com/tfscom/i1/2274485118/O1CN01AbP22B1ng5Xnefvtp_!!2274485118.jpg",
            "https://img.alicdn.com/tfscom/i1/2274485118/O1CN01YlFucX1ng5XoDyFe9_!!2274485118.jpg",
            "https://img.alicdn.com/tfscom/i4/2274485118/O1CN01Sy8fy21ng5Xn467qK_!!2274485118.jpg"
            ]
            },
            status: 1,
            title: "胖女人秋冬装洋气胖mm200斤大码女装 2018新款微胖妹妹针织连衣裙",
            tk_rate: "4.50",
            type: 1,
            user_type: 0,
            volume: 1560,
            zk_final_price: "198.00",
            zk_final_price_wap: "198.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=582145521987",
            nick: "美眉炫酷女装",
            num_iid: 582145521987,
            pict_url: "https://img.alicdn.com/tfscom/i4/1692945001/O1CN01xb9qmG1moVCSe0aaE_!!1692945001.jpg",
            provcity: "北京",
            reserve_price: "288.00",
            seller_id: 1692945001,
            shop_title: "这家大码店",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i4/1692945001/O1CN01K9cwTB1moVCXWdeJS_!!1692945001.jpg",
            "https://img.alicdn.com/tfscom/i1/1692945001/O1CN010026Ek1moVCbLokUZ_!!1692945001.jpg",
            "https://img.alicdn.com/tfscom/i4/1692945001/O1CN01ifcEjg1moVCZHop20_!!1692945001.jpg",
            "https://img.alicdn.com/tfscom/i3/1692945001/O1CN010ZBEp81moVCYrV3YG_!!1692945001.jpg"
            ]
            },
            status: 1,
            title: "特大码女装棉服中长款2018新款冬装加厚棉衣胖妹妹过膝230斤棉袄",
            tk_rate: "2.49",
            type: 4,
            user_type: 0,
            volume: 1712,
            zk_final_price: "199.00",
            zk_final_price_wap: "199.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=579657145491",
            nick: "小衣橱管家",
            num_iid: 579657145491,
            pict_url: "https://img.alicdn.com/tfscom/i1/854951489/O1CN011Ms0CyrieXQFJaQ_!!854951489.jpg",
            provcity: "广东 广州",
            reserve_price: "188.00",
            seller_id: 854951489,
            shop_title: "小衣橱管家",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i1/854951489/O1CN011Ms0D0BlFpi1W81_!!854951489.jpg",
            "https://img.alicdn.com/tfscom/i4/854951489/O1CN011Ms0CzMarazTRpy_!!854951489.jpg",
            "https://img.alicdn.com/tfscom/i1/854951489/O1CN011Ms0Czxg5hYo3Jf_!!854951489.jpg",
            "https://img.alicdn.com/tfscom/i3/854951489/O1CN011Ms0CzednSnuxye_!!854951489.jpg"
            ]
            },
            status: 1,
            title: "waitmore高冷气质冷系女装大衣外套港味格子西装毛呢外套赫本风女",
            tk_rate: "3.00",
            type: 1,
            user_type: 0,
            volume: 3165,
            zk_final_price: "188.00",
            zk_final_price_wap: "188.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=583007088959",
            nick: "自然依人19889",
            num_iid: 583007088959,
            pict_url: "https://img.alicdn.com/tfscom/i4/815547271/O1CN01YwHZel23aAHDmaMPm_!!815547271.jpg",
            provcity: "浙江 杭州",
            reserve_price: "580.00",
            seller_id: 815547271,
            shop_title: "索菲欧原创女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i1/815547271/O1CN01n2AUUv23aAHC2zN0I_!!815547271.jpg",
            "https://img.alicdn.com/tfscom/i4/815547271/O1CN01JteMXR23aAHAp03hu_!!815547271.jpg",
            "https://img.alicdn.com/tfscom/i4/815547271/O1CN01pWhHCj23aAH9Wos7x_!!815547271.jpg",
            "https://img.alicdn.com/tfscom/i3/815547271/O1CN01wJgQed23aAHL5CA3r_!!815547271.jpg"
            ]
            },
            status: 1,
            title: "棉衣女中长款2018冬装新款女装chic黑色大毛领小个子羽绒棉服外套",
            tk_rate: "4.50",
            type: 1,
            user_type: 0,
            volume: 1545,
            zk_final_price: "258.00",
            zk_final_price_wap: "258.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=582161127636",
            nick: "chaoming528",
            num_iid: 582161127636,
            pict_url: "https://img.alicdn.com/tfscom/i4/2507973110/O1CN01GAJU4C1YqQExHFbSi_!!2507973110.jpg",
            provcity: "浙江 杭州",
            reserve_price: "1088.00",
            seller_id: 2507973110,
            shop_title: "西西原创女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i1/2507973110/O1CN01aw2fjX1YqQFFMwvtR_!!2507973110.jpg",
            "https://img.alicdn.com/tfscom/i2/2507973110/O1CN01kasgYx1YqQExRkaM5_!!2507973110.jpg",
            "https://img.alicdn.com/tfscom/i4/2507973110/O1CN01B5KLTq1YqQEwdQ3HU_!!2507973110.jpg",
            "https://img.alicdn.com/tfscom/i3/2507973110/O1CN01cZBIam1YqQFNhSQJo_!!2507973110.jpg"
            ]
            },
            status: 1,
            title: "2018新款女装冬中长款仿羊羔毛羊剪绒外套女宽松大码毛领毛毛大衣",
            tk_rate: "3.00",
            type: 4,
            user_type: 0,
            volume: 1482,
            zk_final_price: "328.00",
            zk_final_price_wap: "328.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=578935268077",
            nick: "皮改艳4699",
            num_iid: 578935268077,
            pict_url: "https://img.alicdn.com/tfscom/i1/585373984/O1CN011fIiOKtOeSE2PmQ_!!585373984.jpg",
            provcity: "浙江 杭州",
            reserve_price: "599.00",
            seller_id: 585373984,
            shop_title: "CC美丽公主",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i4/585373984/O1CN011fIiOKhCFGdlxT7_!!585373984.jpg",
            "https://img.alicdn.com/tfscom/i3/585373984/O1CN01dHNgKJ1fIiOo5gW67_!!585373984.jpg",
            "https://img.alicdn.com/tfscom/i2/585373984/O1CN01oDC4pz1fIiOqrwpBw_!!585373984.jpg",
            "https://img.alicdn.com/tfscom/i4/585373984/O1CN011fIiOD3cZ1lUVyv_!!585373984.jpg"
            ]
            },
            status: 1,
            title: "毛呢外套女2018秋冬新款复古休闲系带加厚呢子小西装短款外套女装",
            tk_rate: "4.50",
            type: 1,
            user_type: 0,
            volume: 3650,
            zk_final_price: "168.00",
            zk_final_price_wap: "168.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=584788965989",
            nick: "vipwangting",
            num_iid: 584788965989,
            pict_url: "https://img.alicdn.com/tfscom/i3/112638574/O1CN01zCPEKF2DCwMAL0VE0_!!112638574.jpg",
            provcity: "浙江 嘉兴",
            reserve_price: "198.00",
            seller_id: 112638574,
            shop_title: "羊毛家",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i3/112638574/O1CN01MmHL722DCwMEBliIP_!!112638574.jpg",
            "https://img.alicdn.com/tfscom/i3/112638574/O1CN01VgMk8Q2DCwMCyzrqF_!!112638574.jpg",
            "https://img.alicdn.com/tfscom/i2/112638574/O1CN01pMILuU2DCwMELVAmb_!!112638574.jpg",
            "https://img.alicdn.com/tfscom/i3/112638574/O1CN01BuCRNv2DCwMAjxRmC_!!112638574.jpg"
            ]
            },
            status: 1,
            title: "2019新款女装很仙的法国小众雪纺连衣裙女中长款仙女裙蕾丝裙春装",
            tk_rate: "1.50",
            type: 4,
            user_type: 0,
            volume: 3294,
            zk_final_price: "99.00",
            zk_final_price_wap: "99.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=582749105088",
            nick: "果果一女人",
            num_iid: 582749105088,
            pict_url: "https://img.alicdn.com/tfscom/i2/595412874/O1CN01OMrO3v1X6KlzXrnfc_!!595412874.jpg",
            provcity: "浙江 杭州",
            reserve_price: "249.90",
            seller_id: 595412874,
            shop_title: "果果家 GGWOMEN定制 韩范精品女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i4/595412874/O1CN01f07Klw1X6KlzZukCh_!!595412874.jpg",
            "https://img.alicdn.com/tfscom/i3/595412874/O1CN01ZHKfkM1X6Klx2s4pA_!!595412874.jpg",
            "https://img.alicdn.com/tfscom/i2/595412874/O1CN01m03R1U1X6KlzZwgnv_!!595412874.jpg",
            "https://img.alicdn.com/tfscom/i1/595412874/O1CN01btyNGq1X6Km09RNYP_!!595412874.jpg"
            ]
            },
            status: 1,
            title: "女装冬装2018新款韩版小个子闺蜜装格子毛呢外套短款大衣女冬加厚",
            tk_rate: "1.56",
            type: 4,
            user_type: 0,
            volume: 6586,
            zk_final_price: "249.90",
            zk_final_price_wap: "249.90"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=558489402187",
            nick: "百里挑衣_198",
            num_iid: 558489402187,
            pict_url: "https://img.alicdn.com/tfscom/i2/2111084029/TB2HcQqa0qUQKJjSZFIXXcOkFXa_!!2111084029.jpg",
            provcity: "浙江 杭州",
            reserve_price: "389.00",
            seller_id: 2111084029,
            shop_title: "红都衣舍时装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i2/2111084029/O1CN011fdKDLnPMj1bYtM_!!2111084029.jpg",
            "https://img.alicdn.com/tfscom/i4/2111084029/O1CN011fdKDLnNxSj9Ya5_!!2111084029.jpg",
            "https://img.alicdn.com/tfscom/i4/2111084029/O1CN011fdKDMyx0n23yuD_!!2111084029.jpg",
            "https://img.alicdn.com/tfscom/i1/2111084029/TB2YPX5e.F7MKJjSZFLXXcMBVXa_!!2111084029.jpg"
            ]
            },
            status: 1,
            title: "2019春秋新款女装遮肚子冬季连衣裙女加厚小香风针织秋冬款打底裙",
            tk_rate: "1.50",
            type: 1,
            user_type: 0,
            volume: 2701,
            zk_final_price: "89.00",
            zk_final_price_wap: "89.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=581297838159",
            nick: "我是阿武",
            num_iid: 581297838159,
            pict_url: "https://img.alicdn.com/tfscom/i3/37082293/O1CN01A7Nivd1SoEedKMnvy_!!37082293.jpg",
            provcity: "浙江 杭州",
            reserve_price: "772.40",
            seller_id: 37082293,
            shop_title: "我是阿式设计定制女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i2/37082293/O1CN01BFrkVS1SoEePZva8M_!!37082293.jpg",
            "https://img.alicdn.com/tfscom/i1/37082293/O1CN017wfPEn1SoEeRMgUcB_!!37082293.jpg",
            "https://img.alicdn.com/tfscom/i4/37082293/O1CN01GTgFDM1SoEeSgBbX6_!!37082293.jpg",
            "https://img.alicdn.com/tfscom/i2/37082293/O1CN01P1uguU1SoEeQXGl1L_!!37082293.jpg"
            ]
            },
            status: 1,
            title: "原创设计 2018冬季新款女装加厚仿貂绒中长款A字毛呢大衣呢子外套",
            tk_rate: "1.50",
            type: 1,
            user_type: 0,
            volume: 2214,
            zk_final_price: "386.20",
            zk_final_price_wap: "386.20"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=582826822377",
            nick: "银猴小小崽崽",
            num_iid: 582826822377,
            pict_url: "https://img.alicdn.com/tfscom/i1/1593146983/O1CN01AVWniB21SGD03MY3D_!!1593146983.jpg",
            provcity: "浙江 杭州",
            reserve_price: "1299.00",
            seller_id: 1593146983,
            shop_title: "云端锦绣",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i3/1593146983/O1CN01SYuqPe21SGDRtk1PW_!!1593146983.jpg",
            "https://img.alicdn.com/tfscom/i4/1593146983/O1CN01sNe6aZ21SGDBxbCgK_!!1593146983.jpg",
            "https://img.alicdn.com/tfscom/i3/1593146983/O1CN01zYPsiX21SGCyTC6jq_!!1593146983.jpg",
            "https://img.alicdn.com/tfscom/i4/1593146983/O1CN016cGe3m21SGD5MqQRf_!!1593146983.jpg"
            ]
            },
            status: 1,
            title: "短款羽绒服女2018新款冬季女装韩版宽松加厚大毛领时尚收腰外套女",
            tk_rate: "3.00",
            type: 4,
            user_type: 0,
            volume: 2939,
            zk_final_price: "388.00",
            zk_final_price_wap: "388.00"
            },
            {
            event_end_time: "1970-01-01 00:00:00",
            event_start_time: "1970-01-01 00:00:00",
            item_url: "http://item.taobao.com/item.htm?id=582208280598",
            nick: "果果一女人",
            num_iid: 582208280598,
            pict_url: "https://img.alicdn.com/tfscom/i2/595412874/O1CN01GuZGQb1X6KlroYsiD_!!595412874.jpg",
            provcity: "浙江 杭州",
            reserve_price: "129.90",
            seller_id: 595412874,
            shop_title: "果果家 GGWOMEN定制 韩范精品女装",
            small_images: {
            string: [
            "https://img.alicdn.com/tfscom/i1/595412874/O1CN011VoJRl1X6KlsBkqKV_!!595412874.jpg",
            "https://img.alicdn.com/tfscom/i1/595412874/O1CN01ihqZxU1X6Klu2fh7g_!!595412874.jpg",
            "https://img.alicdn.com/tfscom/i2/595412874/O1CN01JPmqCe1X6KlpyMBOr_!!595412874.jpg",
            "https://img.alicdn.com/tfscom/i1/595412874/O1CN01GYkY9C1X6Kltwldhs_!!595412874.jpg"
            ]
            },
            status: 1,
            title: "2018秋冬新款网红女装裙子黑色网纱仙女裙小个子收腰吊带裙连衣裙",
            tk_rate: "1.56",
            type: 4,
            user_type: 0,
            volume: 3384,
            zk_final_price: "123.41",
            zk_final_price_wap: "123.41"
            }
        ]
      }
    }
	}
</script>

<style scoped>
	.scroller {
    height: 450px;
    flex-direction: row;
    margin-right: 20px;
    margin-bottom: 20px;
  }

  .panel {
    
    width: 300px;
    background-color: #fff;
    border-width: 2px;
    border-style: solid;
    border-color: #ebecee;
    margin-left: 20px;
  }

  .item-photo {
    width: 300px;
    height: 300px;
  }

  .item-title {
    margin: 10px;
    font-size: 26;
    font-weight: 500;
    lines:2;
    text-overflow: ellipsis;
  }

  .price {
    flex-direction: row;
    align-items: center;    
  }

  .zk-price {
    color: #fa513a;
    font-size: 32px;
    margin: 5px;
  }

  .reserve-price {
    color: #bbb;
    font-size: 24px;
    margin: 5px;
    text-decoration: line-through;
  }
</style>
