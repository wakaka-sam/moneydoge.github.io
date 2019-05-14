// pages/publish.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    /*命名时，为了方便记忆以及避免名字冲突，我将在快递，求助，跑腿，闲置的属性加上k,q,p,x的前缀*/
    id: '',
    x_src_of_pic: '../../images /upload.png',/*这个是选择闲置物品图片后，将闲置物品图片替换原按钮图片*/
    /*以下是快递需要上传的属性*/
    k_express_loc:'明德园test',
    k_arrive_time:'2018/01/30 11:00:00',
    k_loc:'至善园二号',
    k_num:'4',
    k_pay:'12',
    k_remark:'none',
    k_phone:'123456789',
    k_wechat:'dsbsaj',

    /*以下是求助需要上传的属性*/
    q_title:'',
    q_content:'',
    q_ending_time:'',
    q_pay:'',
    q_phone:'',
    q_wechat:'',
    
    /*以下是跑腿需要上传的属性*/
    p_title:'',
    p_content:'',
    p_ending_time:'',
    p_pay:'',
    p_phone:'',
    p_wechat:'',

    /*以下是闲置需要上传的属性*/
    x_object_name:'',
    x_content:'',
    x_ending_time:'',
    x_pay:'',
    x_phone:'',
    x_wechat:'',
    x_wechat:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that=this;
    var date = new Date('2018/05/11 11:00:00');
    that.setData({
      id:options.id,
      src_of_pic: '../../images/upload.png',
      /*以下是快递需要上传的属性*/
      k_express_loc: '明德园test',
      k_arrive_time: '2018/01/30 11:00:00',
      k_loc: '至善园二号',
      k_num: '4',
      k_pay: '12',
      k_remark: 'none',
      k_phone: '123456789',
      k_wechat: 'dsbsaj',

      /*以下是求助需要上传的属性*/
      q_title: '',
      q_content: '',
      q_ending_time: '',
      q_pay: '',
      q_phone: '',
      q_wechat: '',

      /*以下是跑腿需要上传的属性*/
      p_title: '',
      p_content: '',
      p_ending_time: '',
      p_pay: '',
      p_phone: '',
      p_wechat: '',

      /*以下是闲置需要上传的属性*/
      x_object_name: '',
      x_content: '',
      x_ending_time: '',
      x_pay: '',
      x_phone: '',
      x_wechat: '',
      x_wechat: ''
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
        _this.setData({ src_of_pic: _tempFilePaths[0] })
        console.log(_tempFilePaths[0]);
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
  //获取快递输入的快递点
  k_express_locInput: function (e) {
    this.setData({
      k_express_loc: e.detail.value
    })
  },
  //获取快递输入的快递件数
  k_numInput: function (e) {
    this.setData({
      k_num: e.detail.value
    })
  },
  //获取快递输入的快递送达地址
  k_locInput: function (e) {
    this.setData({
      k_loc: e.detail.value
    })
  },
  //获取快递输入的快递送达时间
  k_arrive_timeInput: function (e) {
    this.setData({
      k_arrive_time: e.detail.value
    })
  },
  //获取快递输入的快递送达报酬
  k_payInput: function (e) {
    this.setData({
      k_pay: e.detail.value
    })
  },
  //获取快递输入的快递发布人微信号
  k_wechatInput: function (e) {
    this.setData({
      k_wechat: e.detail.value
    })
  },
  //获取快递输入的快递发布人手机号
  k_phoneInput: function (e) {
    this.setData({
      k_phone: e.detail.value
    })
  },
  //获取快递输入的快递发布人备注
  k_remarkInput: function (e) {
    this.setData({
      k_remark: e.detail.value
    })
  },

  
  //快递的属性值上传
  gotoupload: function () {
    var that = this;
    wx.request({
      url: "http://172.18.32.138:8080/Create/Expressage",
      method: "POST",
      data: {
        express_loc: that.data.k_express_loc,
       // arrive_time: '2018/01/30 11:00:00',保留此行是为了保留date格式的时间
        arrive_time: that.data.k_arrive_time,
        loc: that.data.k_loc,
        num: that.data.k_num,
        pay: that.data.k_pay,
        remark: that.data.k_remark,
        phone: that.data.k_phone,
        wechat: that.data.k_wechat,
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res.data);
        console.log(that.data.k_express_loc);
        console.log(that.data.k_num);
        console.log(that.data.k_loc);
        console.log(that.data.k_arrive_time);
        console.log(that.data.k_pay);
        console.log(that.data.k_wechat);
        console.log(that.data.k_phone);
        console.log(that.data.k_remark);

        wx.navigateBack({
          delta: 1  //小程序关闭当前页面返回上一页面
        })
      },
    })
  },

  //获取求助输入的信息标题
  k_titleInput: function (e) {
    this.setData({
      k_title: e.detail.value
    })
  },
  //获取求助输入的信息内容
  q_contentInput: function (e) {
    this.setData({
      q_content: e.detail.value
    })
  },
  //获取求助输入的信息截止时间
  q_endinng_timeInput: function (e) {
    this.setData({
      q_endinng_time: e.detail.value
    })
  },
  //获取求助输入的报酬信息
  q_payInput: function (e) {
    this.setData({
      q_pay: e.detail.value
    })
  },
  //获取求助输入的发布人微信号
  q_wechatInput: function (e) {
    this.setData({
      q_wechat: e.detail.value
    })
  },
  //获取求助输入的发布人手机号
  q_phoneInput: function (e) {
    this.setData({
      q_phone: e.detail.value
    })
  },

  //求助的属性值上传
  gotoupload: function () {
    var that = this;
    wx.request({
      url: "http://172.18.32.138:8080/Create/For_help",
      method: "POST",
      data: {
        express_loc: that.data.k_express_loc,
        // arrive_time: '2018/01/30 11:00:00',保留此行是为了保留date格式的时间
        arrive_time: that.data.k_arrive_time,
        loc: that.data.k_loc,
        num: that.data.k_num,
        pay: that.data.k_pay,
        remark: that.data.k_remark,
        phone: that.data.k_phone,
        wechat: that.data.k_wechat,
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res.data);
        console.log(that.data.k_express_loc);
        console.log(that.data.k_num);
        console.log(that.data.k_loc);
        console.log(that.data.k_arrive_time);
        console.log(that.data.k_pay);
        console.log(that.data.k_wechat);
        console.log(that.data.k_phone);
        console.log(that.data.k_remark);

        wx.navigateBack({
          delta: 1  //小程序关闭当前页面返回上一页面
        })
      },
    })
  },

//以下是闲置的物品的数据上传
  gotodeupload4: function () {
    var that = this;
   
    wx.uploadFile({
      url: "http://119.23.218.7:8080/File/Upload",
      filePath:that.data.src_of_pic,
      name: "src_of_image",
      header:{
        "Content-Type": "multipart/form-data",
      },
      formData:{
        "user":"test"
      },
      success:function (res){
        console.log(that.data.src_of_pic);
      }
    })
   
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
       // console.log(res.data);
        wx.navigateBack({
          delta: 1  //小程序关闭当前页面返回上一页面
        })
      },
    })
  }

})