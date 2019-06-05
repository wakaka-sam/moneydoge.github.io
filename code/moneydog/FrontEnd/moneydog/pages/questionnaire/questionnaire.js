// pages/questionnaire/questionnaire.js
Page({
  data: {
    questionnaireName: null,
    questionnaireDesc: null,
    showDialog: false, //弹窗
    questionItem: { type: -1, title: '', a: '', b: '', c: '', d: ''},
    questionList: []
  },
  //选择题目类型弹窗
  toggleDialog() {
    this.setData({
      showDialog: !this.data.showDialog
    })
  },
  //创建单选题
  createSCQ() {
    this.setData({
      isCreateSCQ: true,
      showDialog: false
    })
    wx.navigateTo({
      url: 'create_question?type=0',
    })
    wx.setStorageSync('questionList', this.data.questionList)
  },
  //创建多选题
  createMCQ() {
    this.setData({
      isCreateMCQ: true,
      showDialog: false
    })
    wx.navigateTo({
      url: 'create_question?type=1',
    })
    wx.setStorageSync('questionList', this.data.questionList)
  },
  //创建填空题
  createCompletion() {
    this.setData({
      isCreateCompletion: true,
      showDialog: false
    })
    wx.navigateTo({
      url: 'create_question?type=2',
    })
    wx.setStorageSync('questionList', this.data.questionList)
  },
  saveQuestionnaire() {
    var s_questionList = this.data.s_questionList
    var m_questionList = this.data.m_questionList
    var c_questionList = this.data.c_questionList
    var data = { 
      questionnaireName: this.data.questionnaireName,
      questionnaireDesc: this.data.questionnaireDesc,
      s_questionList, 
      m_questionList, 
      c_questionList
    }
    var datastr = JSON.stringify(data)
    console.log(datastr)
    /*wx.request({
      url: '',
      data: {
        str: datastr
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res)
      }
    })*/
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      questionnaireName: wx.getStorageSync('questionnaireName'),
      questionnaireDesc: wx.getStorageSync('questionnaireDesc'),
      questionList: wx.getStorageSync('questionList')
    })
    if (options.question != null) {
      var question = JSON.parse(options.question)
      this.data.questionList.push(question)
      console.log('push')
    }
    console.log('questionList is')
    console.log(this.data.questionList)
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