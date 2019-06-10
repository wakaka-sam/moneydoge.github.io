// pages/questionnaire/create_question.js
Page({
  data: {
    isCreateSCQ: false,
    isCreateMCQ: false,
    isCreateCompletion: false,
    questionItem: {type: -1, title: '', a:'', b:'', c:'', d:''},
    questionContentCount: { type: -1, a: 0, b: 0, c: 0, d: 0, fill: '' }
  },
  SCQName: function (e) {
    this.data.questionItem.title = e.detail.value
  },
  SCQChoice1: function (e) {
    if (e.detail.value)
      this.data.questionItem.a = e.detail.value
  },
  SCQChoice2: function (e) {
    if (e.detail.value)
      this.data.questionItem.b = e.detail.value
  },
  SCQChoice3: function (e) {
    if (e.detail.value)
      this.data.questionItem.c = e.detail.value
  },
  SCQChoice4: function (e) {
    if (e.detail.value)
      this.data.questionItem.d = e.detail.value
  },
  //确认单选题
  confirmSCQ() {
    //设置题目类型
    this.data.questionItem.type = 0 
    this.data.questionContentCount.type = 0
    this.setData({
      questionItem: this.data.questionItem,
      questionContentCount: this.data.questionContentCount
    })
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];  //上一个页面
    //设置上一个页面的问题列表questionList和选项列表questionContentCountList
    prevPage.data.questionList.push(this.data.questionItem)
    prevPage.data.questionContentCountList.push(this.data.questionContentCount)
    prevPage.setData({
      questionList: prevPage.data.questionList,
      questionContentCountList: prevPage.data.questionContentCountList,
    })
    //跳转回上一个页面
    wx.navigateBack({})
  },
  //确认多选题
  confirmMCQ() {
    //设置题目类型
    this.data.questionItem.type = 1
    this.data.questionContentCount.type = 1
    this.setData({
      questionItem: this.data.questionItem,
      questionContentCount: this.data.questionContentCount
    })
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];  //上一个页面
    //设置上一个页面的问题列表questionList和选项列表questionContentCountList
    prevPage.data.questionList.push(this.data.questionItem)
    prevPage.data.questionContentCountList.push(this.data.questionContentCount)
    prevPage.setData({
      questionList: prevPage.data.questionList,
      questionContentCountList: prevPage.data.questionContentCountList,
    })
    //跳转回上一个页面
    wx.navigateBack({})
  },
  //确认填空题
  confirmCompletion() {
    //设置题目类型
    this.data.questionItem.type = 2
    this.data.questionContentCount.type = 2
    this.setData({
      questionItem: this.data.questionItem,
      questionContentCount: this.data.questionContentCount
    })
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];  //上一个页面
    //设置上一个页面的问题列表questionList和选项列表questionContentCountList
    prevPage.data.questionList.push(this.data.questionItem)
    prevPage.data.questionContentCountList.push(this.data.questionContentCount)
    prevPage.setData({
      questionList: prevPage.data.questionList,
      questionContentCountList: prevPage.data.questionContentCountList,
    })
    //跳转回上一个页面
    wx.navigateBack({})
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if(options.type == 0) {
      this.setData({
        isCreateSCQ: true,
        isCreateMCQ: false,
        isCreateCompletion: false
      })
    }
    if (options.type == 1) {
      this.setData({
        isCreateSCQ: false,
        isCreateMCQ: true,
        isCreateCompletion: false
      })
    }
    if (options.type == 2) {
      this.setData({
        isCreateSCQ: false,
        isCreateMCQ: false,
        isCreateCompletion: true
      })
    }
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