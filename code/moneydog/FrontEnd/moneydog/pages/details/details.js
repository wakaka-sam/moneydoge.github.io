const app = getApp()
const baseUrl = '172.18.32.138'
// pages/details.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sessionId: '847694c4-14dd-47b2-8922-facd8e379f47',
    showDialog: false,
    id: 0,//1:快递 2:跑腿 3:求助 4:闲置 5:问卷
    json: [],
    qid: 0,//问卷id
    name: '调查问卷',
    description: '饭堂满意度',
    content: [],
    content_count: [],
    imgurl: 'a58e416d-64fd-444e-854b-9d36e3ae27291.jpg'
  },

  //获取联系方式
  getContact: function (e) {
    var that = this
    var options = e.currentTarget.dataset
    console.log(options)
    var url = 'http://' + baseUrl + ':8080/Load/Contact_way?type=' + 
              options.type + '&id=' + options.id
    console.log(url, 'sessionId:', that.data.sessionId)
    wx.request({
      url: url,
      header:{sessionId: that.data.sessionId},
      success: function (res) {
        that.setData({
          phone: res.data[0].phone,
          wechat: res.data[0].wechat
        })
        that.toggleDialog()
      },
      fail: function (res) {
        console.log('fail to get Contact_way', res)
      }
    })
  },
  /**
  * 控制 pop 的打开关闭
  * 该方法作用有两个:
  * 1：点击弹窗以外的位置可消失弹窗
  * 2：用到弹出或者关闭弹窗的业务逻辑时都可调用
  */
  toggleDialog: function() {
    this.setData({
      showDialog: !this.data.showDialog
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const sessionId = wx.getStorageSync('SessionId')
    if (sessionId) {
      this.setData({ sessionId: sessionId })
    }
    const json = wx.getStorageSync('detailJson')
    if (!json) {
      wx.showToast({
           title: '加载失败',
           icon: 'none',
           duration: 2000//持续的时间
      })
      wx.navigateBack({
          delta: 1
      })
    }
    if (options.id != 5) {
      this.setData({
        id: options.id,
        json: json
      })
    }
    else {
      var that = this
      that.setData({qid: json.qid})
      var url = 'http://' + baseUrl + ':8080/Create/getQuestionairContent?id='
      url +=  that.data.qid
      console.log(url)
      wx.request({
        url: url,
        success: function (res) {
          that.setData({
            content: res.data.content,
            description: res.data.description,
            name: res.data.name})},
        fail:function (res) {
          console.log('fail to get the questionnaire')}
      })
    }
    console.log(this.data)
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

  }
})