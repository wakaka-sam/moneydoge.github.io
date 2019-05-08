// pages/publish.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: '',
    src_of_pic: '../../images /upload.png',/*这个是选择闲置物品图片后，将闲置物品图片替换原按钮图片*/
    /*以下是快递需要上传的属性*/
    express_loc:'明德园test',
    arrive_time:'2018/01/30 11:00:00',
    loc:'至善园二号',
    num:'4',
    pay:'12',
    remark:'none',
    phone:'123456789',
    wechat:'dsbsaj',

     /*以下是闲置需要上传的属性*/
    object_name:'',
    content:'',
    ending_time:'',
    pay2:'',
    phone2:'',
    wechat:'',
    wechat2:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that=this;
    var date = new Date('2018/01/30 11:00:00');
    that.setData({
      id:options.id,
      src_of_pic: '../../images/upload.png'
    })
    console.log(that.data.id)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /*
    点击图片选择按钮响应，选择本地图片或者使用摄像机
  */
  choose_images: function () {
    var _this = this
    wx.chooseImage({
      count: 1, // 最多可以选择的图片张数，默认9
      sizeType: ['original', 'compressed'], // original 原图，compressed 压缩图，默认二者都有
      sourceType: ['album', 'camera'], // album 从相册选图，camera 使用相机，默认二者都有
      success: function (res) {
        // success
        var _tempFilePaths = res.tempFilePaths;
        _this.setData({ src_of_pic: _tempFilePaths })
       
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        console.log(JSON.stringify(res));
      },
      fail: function () {
        // fail
        
      },
      complete: function () {
        // complete
        //选择成功则更新物品图片
       
      }
    })
  },
  gotoupload: function () {

    wx.request({
      url: "http://172.18.32.138:8080/Create/Expressage",
      method: "POST",
      data: {
        express_loc: '明德园test',
        arrive_time: '2018/01/30 11:00:00',
        loc: '至善园二号',
        num: '4',
        pay: '12',
        remark: 'none',
        phone: '123456789',
        wechat: 'dsbsaj',
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res.data);
        wx.navigateBack({
          delta: 1  //小程序关闭当前页面返回上一页面
        })
      },
    })
  },
//以下是闲置的物品的数据上传
  gotodeupload4: function () {
    var that = this;
   /*
    wx.uploadFile({
      url: '',
      filePath: '',
      name: '',
    },success(res){
    url = yumig  res.imageUrl;
    })
   */ 
    wx.request({
      url: "http://172.18.32.138:8080/Create/Second_hand",
      method: "POST",
      data: {
        object_name: '自走车',
        content: '你说啥？',
        ending_time: '2018/01/30 11:00:00',
        pay: '10',
        phone: '12334456',
        wechat: 'dsbasd',
        photo_url:that.data.src_of_pic
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res.data);
        wx.navigateBack({
          delta: 1  //小程序关闭当前页面返回上一页面
        })
      },
    })
  }

})