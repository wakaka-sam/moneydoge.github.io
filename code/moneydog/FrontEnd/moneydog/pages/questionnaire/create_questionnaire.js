// pages/questionnaire/create_questionnaire.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    questionnaireName: null,
    questionnaireDesc: null,
    questionnairePay: null,
    questionnaireTota: null
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
    var title = ''
    var tag = 0
    //判断问卷名是否为空
    if(this.data.questionnaireName == null && tag == 0) {
      title = '问卷名'
      tag = 1
    } else if (this.data.questionnaireName == null && tag == 1) {
      title += '、问卷名'
    }
    //判断问卷描述是否为空
    if (this.data.questionnaireDesc == null && tag == 0) {
      title = '问卷描述'
      tag = 1
    } else if (this.data.questionnaireDesc == null && tag == 1) {
      title += '、问卷描述'
    }
    //判断问卷报酬是否为空
    if (this.data.questionnairePay == null && tag == 0) {
      title = '问卷报酬'
      tag = 1
    } else if (this.data.questionnairePay == null && tag == 1) {
      title += '、问卷报酬'
    }
    //判断预期填写份数是否为空
    if (this.data.questionnaireTota == null && tag == 0) {
      title = '预期填写份数'
      tag = 1
    } else if (this.data.questionnaireTota == null && tag == 1) {
      title += '、预期填写份数'
    }
    //其中一个为空，出现提示
    if (title != '') {
      title += '不能为空'
      wx.showToast({
        title: title,
        icon: 'none'
      })
    }
    //全部都不为空，可成功创建进入问卷题目添加页面
    else {
      wx.setStorageSync('questionnaireName', this.data.questionnaireName)
      wx.setStorageSync('questionnaireDesc', this.data.questionnaireDesc)
      wx.setStorageSync('questionnairePay', this.data.questionnairePay)
      wx.setStorageSync('questionnaireTota', this.data.questionnaireTota)
      wx.setStorageSync('questionList', [])
      wx.setStorageSync('questionContentCountList', [])
      wx.redirectTo({
        url: 'questionnaire',
      })
    }
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