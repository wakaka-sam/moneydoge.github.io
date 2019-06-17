//获取应用实例
const app = getApp()

Page({
  data: {
    sessionId: '',
    flag: true,
    flag1: true,
    isFinish: false,
    orderid:-1,
    ordertype: null,
    isSelected1: true,//默认快递
    isSelected2: false,//跑腿
    isSelected3: false,//求助
    isSelected4: false,//闲置
    isSelected5: false,//问卷
    isSelected6: true,//我的发布
    isSelected7: false,//我的接单
    publishExTradeList: [],//发布的快递订单
    publishErTradeList: [],//发布的跑腿订单
    publishHeTradeList: [],//发布的求助订单
    publishSeTradeList: [],//发布的闲置订单
    publishQuTradeList: [],//发布的问卷订单
    receiptExTradeList: [],//接收的快递订单
    receiptErTradeList: [],//接收的跑腿订单
    receiptHeTradeList: [],//接收的求助订单
    receiptSeTradeList: []//接收的闲置订单
  },
  select1: function () {
    this.setData({
      isSelected1: true,
      isSelected2: false,
      isSelected3: false,
      isSelected4: false,
      isSelected5: false
    })
  },
  select2: function () {
    this.setData({
      isSelected1: false,
      isSelected2: true,
      isSelected3: false,
      isSelected4: false,
      isSelected5: false
    })
  },
  select3: function () {
    this.setData({
      isSelected1: false,
      isSelected2: false,
      isSelected3: true,
      isSelected4: false,
      isSelected5: false
    })
  },
  select4: function () {
    this.setData({
      isSelected1: false,
      isSelected2: false,
      isSelected3: false,
      isSelected4: true,
      isSelected5: false
    })
  },
  select5: function () {
    this.setData({
      isSelected1: false,
      isSelected2: false,
      isSelected3: false,
      isSelected4: false,
      isSelected5: true
    })
  },
  select6: function() {
    this.setData({
      isSelected1: true,
      isSelected2: false,
      isSelected3: false,
      isSelected4: false,
      isSelected5: false,
      isSelected6:true,
      isSelected7:false
    })
    this.OnLoadPublishOrders()
  },
  select7: function () {
    this.setData({
      isSelected1: true,
      isSelected2: false,
      isSelected3: false,
      isSelected4: false,
      isSelected5: false,
      isSelected6:false,
      isSelected7: true
    })
    this.OnLoadReceiptOrders()
  },
  //确认完成
  confirmFinish: function(e) {
    var that = this
    var options = e.currentTarget.dataset
    var url = 'https://moneydog.club:3030/Modified/FinishIssue?type=' + options.type + '&id=' + options.orderid
    console.log(url)
    wx.request({
      url: url,
      header: { sessionId: that.data.sessionId },
      method: 'PUT',
      success: function (res) {
        console.log('确认成功')
        that.OnLoadPublishOrders()
        that.OnLoadReceiptOrders()
      }
    })
  },
  //取消删除订单
  cancelDelete: function() {
    this.setData({
      flag: !this.data.flag
    })
    console.log("cancel delete")
  },
  //确认删除订单
  confirmDelete: function () {
    this.setData({
      flag: !this.data.flag
    })
    var that = this
    var url = 'https://moneydog.club:3030/Modified/DeleteIssue?type=' + that.data.ordertype + '&id=' + that.data.orderid
    console.log(url)
    wx.request({
      url: url,
      header: { sessionId: that.data.sessionId },
      method: 'PUT',
      success: function (res) {
        console.log("confirm delete")
        that.OnLoadPublishOrders()
        that.OnLoadReceiptOrders()
        console.log(res)
      }
    })
  },
  //长按删除
  deleteOrder: function (e) {
    if(isSelected6 == true) {
      console.log(e.currentTarget.dataset)
      this.setData({
        flag: !this.data.flag,
        orderid: e.currentTarget.dataset.orderid,
        ordertype: e.currentTarget.dataset.type
      })
    }
  },
  //显示问卷内容
  show_content: function(e) {
    var that = this
    wx.request({
      url: "https://moneydog.club:3030/Create/viewAll",
      header: { sessionId: that.data.sessionId },
      data: { id: that.data.orderid },
      success: function (res) {
        console.log(res.data)
        wx.setStorageSync('questionnaireName', res.data.viewAllList.name)
        wx.setStorageSync('questionnaireDesc', res.data.viewAllList.description)
        wx.setStorageSync('questionnairePay', 0)
        wx.setStorageSync('questionnaireTota', res.data.viewAllList.num)
        wx.setStorageSync('questionList', JSON.parse(res.data.viewAllList.content))
        wx.navigateTo({
          url: '../../questionnaire/questionnaire',
        })
      }
    })
    /*wx.navigateTo({
      url: 'questionnaire',
    })*/
  },
  //显示问卷结果
  show_result: function (e) {

  },
  //终止问卷
  end_questionnaire: function (e) {

  },
  //删除问卷
  delete_questionnaire: function (e) {

  },
  //加载发布订单
  OnLoadPublishOrders: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: "https://moneydog.club:3030/Load/Creation",
      header: { sessionId: that.data.sessionId },
      success: function (res) {
        console.log(res.data)
        that.setData({
          publishExTradeList: res.data.expressages,//发布的快递订单
          publishErTradeList: res.data.errands,//发布的跑腿订单
          publishHeTradeList: res.data.for_helps,//发布的求助订单
          publishSeTradeList: res.data.second_hands,//发布的闲置订单
        })
        resolve()
      },
      fail: function (res) {
        console.log('fail to onload', res)
      }
    }))
  },
  //加载接收订单
  OnLoadReceiptOrders: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: "https://moneydog.club:3030/Load/Receiving",
      header: { sessionId: that.data.sessionId },
      success: function (res) {
        console.log(res.data)
        that.setData({
          receiptExTradeList: res.data.expressages,//接收的快递订单
          receiptErTradeList: res.data.errands,//接收的跑腿订单
          receiptHeTradeList: res.data.for_helps,//接收的求助订单
          receiptSeTradeList: res.data.second_hands//接收的闲置订单
        })
        resolve()
      },
      fail: function (res) {
        console.log('fail to onload', res)
      }
    }))
  },
  //加载我发布问卷
  OnLoadQuestionnaires: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: "https://moneydog.club:3030/Create/LoadMyQuestionair",
      data: { sessionId: that.data.sessionId },
      success: function (res) {
        console.log(res.data)
        that.setData({
          publishQuTradeList: res.data
        })
        resolve()
      },
      fail: function (res) {
        console.log('fail to onload', res)
      }
    }))
  },
  //跳转详情页面
  showDetail: function (e) {
    var trade = e.currentTarget.dataset
    var json = trade.json
    var id = trade.id
    console.log(id, json)
    wx.navigateTo({
      url: '../../details/details?id=' + id + '&json=' + JSON.stringify(json),
    })
  },
  showPopup: function(e) {
    this.setData({
      flag1: false,
      orderid: e.currentTarget.dataset.id
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const app = getApp()
    const sessionId = wx.getStorageSync('SessionId')
    if (sessionId) {
      this.setData({ sessionId: sessionId })
    }
    var that = this
    var id = options.id
    if (id == 1)
      that.select1()
    else if (id == 2)
      that.select2()
    else if (id == 3)
      that.select3()
    else if (id == 4)
      that.select4()
    else
      that.select5()
    
    that.OnLoadPublishOrders()
    that.OnLoadQuestionnaires()
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
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }

})