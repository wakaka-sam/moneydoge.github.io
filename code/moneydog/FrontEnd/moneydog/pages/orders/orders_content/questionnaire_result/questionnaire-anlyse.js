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
    contentCount: [],
    jointResult: []
  },
  jointQuestionnaireResult: function() {
    for(var i = 0;i < this.data.questionList.length;i++) {
      var resultItem = {question: this.data.questionList[i], result: this.data.contentCount[i]}
      this.data.jointResult.push(resultItem)
    }
    this.setData({
      jointResult: this.data.jointResult
    })
  },
  showCompletionResult: function(e) {
    var fill = JSON.stringify(this.data.contentCount[e.currentTarget.dataset.id].fill)
    console.log(fill)
    wx.navigateTo({
      url: 'completion_results?fill=' + fill,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      questionnaireName: wx.getStorageSync('questionnaireName'),
      questionnaireDesc: wx.getStorageSync('questionnaireDesc'),
      questionnaireTota: wx.getStorageSync('questionnaireTota'),
      questionList: wx.getStorageSync('questionList'),
      contentCount: wx.getStorageSync('questionContentCountList'),
    })
    this.jointQuestionnaireResult()
    console.log(this.data.jointResult)
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