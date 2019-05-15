// pages/questionnaire/questionnaire.js
Page({
  data: {
    questionnaireName: null,
    questionnaireDesc: null,
    isCreate: false,
    showDialog: false, //弹窗
    isCreateSCQ: false,
    isCreateMCQ: false,
    isCreateCompletion: false,
    questionItem: {question: null, choices: []},
    choices: [{ name: null, value: null }, { name: null, value: null }, { name: null, value: null }, { name: null, value: null }],
    s_questionList: [
      { question: '题目1', choices: [{ name: 'CHN', value: '中国'}, { name: 'BRA', value: '巴西' }]}
    ],
    m_questionList: [
      { question: '题目2', choices: [{ name: 'CHN', value: '中国' }, { name: 'BRA', value: '巴西' }]}
    ],
    c_questionList: [
      { question: '题目3'}
    ]
  },
  //创建问卷
  createQuestionnaire:function() {
    this.setData({
      isCreate: true,
      questionnaireName: this.data.questionnaireName,
      questionnaireDesc: this.data.questionnaireDesc
    })
  },
  //选择题目类型弹窗
  toggleDialog() {
    this.setData({
      showDialog: !this.data.showDialog
    })
  },
  questionnaireName: function(e) {
    this.data.questionnaireName = e.detail.value
  },
  questionnaireDesc: function(e) {
    this.data.questionnaireDesc = e.detail.value
  },
  SCQName: function(e) {
    this.data.questionItem.question = e.detail.value
  },
  SCQChoice1: function (e) {
    if(e.detail.value)
      this.data.choices[0] = { name: e.detail.value, value: e.detail.value }
  },
  SCQChoice2: function (e) {
    if (e.detail.value)
      this.data.choices[1] = { name: e.detail.value, value: e.detail.value }
  },
  SCQChoice3: function (e) {
    if (e.detail.value)
      this.data.choices[2] = { name: e.detail.value, value: e.detail.value }
  },
  SCQChoice4: function (e) {
    if (e.detail.value)
      this.data.choices[3] = { name: e.detail.value, value: e.detail.value }
  },
  //创建单选题
  createSCQ() {
    this.setData({
      isCreateSCQ: true,
      showDialog: false
    })
  },
  //创建多选题
  createMCQ() {
    this.setData({
      isCreateMCQ: true,
      showDialog: false
    })
  },
  //创建填空题
  createCompletion() {
    this.setData({
      isCreateCompletion: true,
      showDialog: false
    })
  },
  //确认单选题
  confirmSCQ() {
    for (var index in this.data.choices) {
      if (this.data.choices[index].value!=null)
        this.data.questionItem.choices.push(this.data.choices[index])
    }
    this.data.s_questionList.push(this.data.questionItem)
    this.setData({
      isCreateSCQ: false,
      s_questionList: this.data.s_questionList,
      questionItem: { question: null, choices: [] },
      choices: [{ name: null, value: null }, { name: null, value: null }, { name: null, value: null }, { name: null, value: null }]
    })
  },
  //确认多选题
  confirmMCQ() {
    for (var index in this.data.choices) {
      if (this.data.choices[index].value != null)
        this.data.questionItem.choices.push(this.data.choices[index])
    }
    this.data.m_questionList.push(this.data.questionItem)
    this.setData({
      isCreateMCQ: false,
      m_questionList: this.data.m_questionList,
      questionItem: { question: null, choices: [] },
      choices: [{ name: null, value: null }, { name: null, value: null }, { name: null, value: null }, { name: null, value: null }]
    })
  },
  //确认填空题
  confirmCompletion() {
    if(this.data.questionItem.question != null)
      this.data.c_questionList.push({ question: this.data.questionItem.question })
    this.setData({
      isCreateCompletion: false,
      c_questionList: this.data.c_questionList,
      questionItem: { question: null, choices: [] }
    })
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