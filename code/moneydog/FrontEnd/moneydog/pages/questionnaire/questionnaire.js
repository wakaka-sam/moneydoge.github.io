// pages/questionnaire/questionnaire.js
Page({
  data: {
    questionnaireName: null,
    questionnaireDesc: null,
    questionnairePay: 0,
    questionnaireTota: 0,
    showDialog: false, //弹窗
    questionItem: { type: -1, title: '', a: '', b: '', c: '', d: ''},
    questionList: [],
    contentCount: []
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
  //保存问卷
  saveQuestionnaire() {
    var that = this
    var data = { 
      name: this.data.questionnaireName,
      description: this.data.questionnaireDesc,
      pay: this.data.questionnairePay,
      content: JSON.stringify(this.data.questionList),
      content_count: JSON.stringify({ content_count: this.data.contentCount }),
      total_num: this.data.questionnaireTota
    }
    console.log(data)
    var t1 = JSON.stringify(this.data.questionList)
    var t2 = JSON.stringify({ content_count: this.data.contentCount })
    wx.request({
      url: 'https://moneydog.club:3030/Create/questionair',
      data: {
        name: this.data.questionnaireName,
        description: this.data.questionnaireDesc,
        pay: 4,
        content: t1,
        content_count: t2,
        total_num: this.data.questionnaireTota
      },
      method: 'POST', 
      header: { sessionId: wx.getStorageSync('SessionId'), "Content-Type": "application/x-www-form-urlencoded" },
      success: function (res) {
        console.log(res);
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
    if (options.question != null) {
      var question = JSON.parse(options.question)
      this.data.questionList.push(question)
      wx.setStorageSync('questionList', this.data.questionList)
    }
    this.setData({
      questionList: this.data.questionList
    })
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