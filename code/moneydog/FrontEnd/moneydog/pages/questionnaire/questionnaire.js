// pages/questionnaire/questionnaire.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isCreate: false,
    showDialog: false, //弹窗
    isCreateSCQ: false,
    isCreateMCQ: false,
    isCreateCompletion: false
  },
  //创建问卷
  createQuestionnaire:function() {
    this.setData({
      isCreate: true
    })
  },
  //选择题目类型弹窗
  toggleDialog() {
    this.setData({
      showDialog: !this.data.showDialog
    })
  },
  createSCQ() {
    this.setData({
      isCreateSCQ: true,
      showDialog: false
    })
  },
  createMCQ() {
    this.setData({
      isCreateMCQ: true,
      showDialog: false
    })
  },
  createCompletion() {
    this.setData({
      isCreateCompletion: true,
      showDialog: false
    })
  },
  confirmSCQ() {
    this.setData({
      isCreateSCQ: false
    })
  },
  confirmMCQ() {
    this.setData({
      isCreateMCQ: false
    })
  },
  confirmCompletion() {
    this.setData({
      isCreateCompletion: false
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