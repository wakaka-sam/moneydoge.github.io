// pages/questionnaire/create_questionnaire.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    questionnaireName: null,
    questionnaireDesc: null,
    questionnairePay: 0,
    questionnaireTota: 0
  },
  questionnaireName: function (e) {
    this.data.questionnaireName = e.detail.value
  },
  questionnaireDesc: function (e) {
    this.data.questionnaireDesc = e.detail.value
  },
  questionnairePay: function (e) {
    this.data.questionnairePay = e.detail.value
  },
  questionnaireTota: function (e) {
    this.data.questionnaireTota = e.detail.value
  },
  //创建问卷
  createQuestionnaire: function () {
    wx.setStorageSync('questionnaireName', this.data.questionnaireName)
    wx.setStorageSync('questionnaireDesc', this.data.questionnaireDesc)
    wx.setStorageSync('questionnairePay', this.data.questionnairePay)
    wx.setStorageSync('questionnaireTota', this.data.questionnaireTota)
    wx.setStorageSync('questionList', [])
    wx.setStorageSync('questionContentCountList', [])
    wx.redirectTo({
      url: 'questionnaire',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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