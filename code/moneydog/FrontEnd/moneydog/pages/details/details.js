const app = getApp()
const baseUrl = '172.18.32.138'
// pages/details.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    showDialog: false,
    id: 0,//1:快递 2:跑腿 3:求助 4:闲置 5:问卷
    json: [],
    imgurl: 'a58e416d-64fd-444e-854b-9d36e3ae27291.jpg'
  },

  //获取联系方式
  getContact: function (e) {
    var that = this
    var options = e.currentTarget.dataset
    console.log(options)
    var url = 'http://' + baseUrl + ':8080/Load/Contact_way?type=' + 
              options.type + '&id=' + options.id
    console.log(url)
    var sessionId = app.globalData.sessionId
    wx.request({
      url: url,
      header:{sessionId:sessionId},
      success: function (res) {
        that.setData({
          phone: res.data[0].phone,
          wechat: res.data[0].wechat
        })
        that.toggleDialog()
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
    this.setData({
      id: options.id,
      json: JSON.parse(options.json)
    })
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