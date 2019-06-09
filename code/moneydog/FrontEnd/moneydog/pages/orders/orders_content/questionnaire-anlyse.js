// pages/orders/orders_content/questionnaire-anlyse.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    questionnaireName: null,
    questionnaireDesc: null,
    questionnairePay: 0,
    questionnaireTota: 0,
    showDialog: false, //弹窗
    questionItem: { type: -1, title: '', a: '', b: '', c: '', d: '' },
    questionList: [],
    contentCount: []
  },
  onloadQuestionnaireResult: function() {
    var that = this
    wx.request({
      url: "https://moneydog.club:3030/Create/viewAll",
      header: { sessionId: that.data.sessionId },
      data: { id: that.data.orderid },
      success: function (res) {
        console.log(res.data.viewAllList.content_count)
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      questionnaireName: wx.getStorageSync('questionnaireName'),
      questionnaireDesc: wx.getStorageSync('questionnaireDesc'),
      questionnairePay: wx.getStorageSync('questionnairePay'),
      questionnaireTota: wx.getStorageSync('questionnaireTota'),
      questionList: wx.getStorageSync('questionList')
    })
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