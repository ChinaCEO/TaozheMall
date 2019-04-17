/**
 * 本地图片库
 */

const baseUrl = 'file:///android_asset/images/'

const imgLocationSrc = {
	banners: {
		homeTopBanners: [
      baseUrl + 'banners/111.jpg',
      baseUrl + 'banners/222.jpg',
      baseUrl + 'banners/333.jpg',
      baseUrl + 'banners/444.jpg'
    ],
    homeTopBannersBottomMask: baseUrl + 'banners/slider-mask.png',    
  },
  find: {
    famous: baseUrl + 'banners/famous.jpg',
    fashion: baseUrl + 'banners/fashion.jpg',
    woman: baseUrl + 'banners/woman.jpg',
    infant: baseUrl + 'banners/infant.jpg',
  },
  category: {
    dianNaoPeijian: baseUrl + 'category/电脑配件.png',
    jiaDian: baseUrl + 'category/电脑配件.png',
    jiaFang: baseUrl + 'category/家纺.png',
    jiaJuJiaZhuang: baseUrl + 'category/家居家装.png',
    meiZhuangGeHu: baseUrl + 'category/美妆个护.png',
    muYing: baseUrl + 'category/母婴.png',
    nanXie: baseUrl + 'category/男鞋.png',
    nanZhuang: baseUrl + 'category/男装.png',
    neiYi: baseUrl + 'category/内衣.png',
    nvXie: baseUrl + 'category/女鞋.png',
    nvZhuang: baseUrl + 'category/女装.png',
    peiShi: baseUrl + 'category/配饰.png',
    qiCheYongPin: baseUrl + 'category/汽车用品.png',
    shiPin: baseUrl + 'category/食品.png',
    shouJi: baseUrl + 'category/手机.png',
    tuShu: baseUrl + 'category/图书.png',
    yuDongxie: baseUrl + 'category/运动鞋.png',
    yuDongYi: baseUrl + 'category/运动衣.png'    
  },
  gif: {
    loading: baseUrl + 'gif/loading.gif',
    pullLoading: baseUrl + 'gif/pull-loading.gif'
  },
  goodsPics: {
    sale1: baseUrl + 'goods_pics/sale1.png'
  },
  header: {
    fashion: baseUrl + 'header/fashion.jpg',
    findHeader: baseUrl + 'header/find-header.jpg',
    infant: baseUrl + 'header/infant.png',
    loadinBg: baseUrl + 'header/loadin-bg.png',
    renqi: baseUrl + 'header/renqi.png'
  },
  icon: {
    chunk_99: baseUrl + 'icon/99.png',
    chunk_199: baseUrl + 'icon/199.png',
    chunk_baokuan: baseUrl + 'icon/baokuan.png',
    chunk_jingxuan: baseUrl + 'icon/jingxuan.png',
    chunk_pinpai: baseUrl + 'icon/pinpai.png'
  },
  logo: {
    mallLogo: baseUrl + 'logo/logo.png',
    snapLogo: baseUrl + 'logo/snap-logo.png',
    tmallLogo: baseUrl + 'logo/tmall-logo.jpg'
  },
  title: {
    findHotTitle: baseUrl + 'title/find-hot-title.png',
    findSliderTitle: baseUrl + 'title/find-slider-title.png',
    nanZhuangRec: baseUrl + 'title/nanzhuang.png',
    nvZhuangRec: baseUrl + 'title/nvzhuang.png',
    haoQuanTuiJian: baseUrl + 'title/haoquantuijian.png'
  }
}

export default imgLocationSrc;