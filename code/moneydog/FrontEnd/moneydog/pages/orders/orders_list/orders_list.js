//获取应用实例
const app = getApp()
console.log(wx.getStorageSync('SessionId'))

Page({
  /**
   * 页面的初始数据
   */
  data: {
    sessionId: '847694c4-14dd-47b2-8922-facd8e379f47',
    flag: true,
    isFinish: false,
    index: -1,
    ordertype: null,
    isSelected1: true,//默认快递
    isSelected2: false,//跑腿
    isSelected3: false,//求助
    isSelected4: false,//闲置
    isSelected5: false,//问卷
    isSelected6: true,//我的发布
    isSelected7: false,//我的接单
    lastId1: -1,//获取订单列表后得到
    lastId2: -1,//列表最后一个元素的
    lastId3: -1,//订单号，并存起来，
    lastId4: -1,//默认值-1，当得到所
    lastId5: -1,//有订单后也置为-1
    exTradeList: [
      {
        "express_loc": "明德园6号",
        "arrive_time": "2019-05-03",
        "num": 4,
        "pay": 10,
        "loc": "至善园2号666",
        "pid": 4,
        "remark": "none",
        "issue_time": "2019-05-3",
        "state": 0
      },
      {
        "express_loc": "明德园6号",
        "arrive_time": "2019-05-04",
        "num": 1,
        "pay": 15,
        "loc": "至善园2号666",
        "pid": 5,
        "remark": "none",
        "issue_time": "2019-05-4",
        "state": 0
      }],
    erTradeList: [],
    heTradeList: [],
    seTradeList: []
  },
  /**
   * 页面切换
   */
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
      isSelected6:true,
      isSelected7:false
    })
    var that = this
    that.setData({
      erTradeList: null,
      exTradeList: null,
      heTradeList: null,
      seTradeList: null
    })
    console.log(that.data.sessionId)
    wx.request({
      url: "http://172.18.32.138:8080/Load/Creation",
      header: { sessionId: that.data.sessionId },
      success: function (res) {
        console.log(res.data)
        that.setData({
          erTradeList: res.data.errands,
          exTradeList: res.data.expressages,
          heTradeList: res.data.for_helps,
          seTradeList: res.data.second_hands
        })
      }
    })
  },
  select7: function () {
    this.setData({
      isSelected6:false,
      isSelected7: true
    })
    var that = this
    that.setData({
      erTradeList: null,
      exTradeList: null,
      heTradeList: null,
      seTradeList: null
    })
    console.log(that.data.sessionId)
    wx.request({
      url: "http://172.18.32.138:8080/Load/Receiving", 
      header: { sessionId: that.data.sessionId },
      success: function (res) {
        console.log(res.data)
        that.setData({
          erTradeList: res.data.errands,
          exTradeList: res.data.expressages,
          heTradeList: res.data.for_helps,
          seTradeList: res.data.second_hands
        })
      }
    })
  },
  confirmFinish: function(e) {
    var that = this
    var options = e.currentTarget.dataset
    var url = 'http://172.18.32.138:8080/Modified/FinishIssue?type=' + options.type + '&id=' + options.orderid
    console.log(url)
    wx.request({
      url: url,
      header: { sessionId: that.data.sessionId },
      method: 'PUT',
      success: function (res) {
        console.log('确认成功' + options.id)
        wx.request({
          url: "http://172.18.32.138:8080/Load/Receiving",
          header: { sessionId: that.data.sessionId },
          success: function (res) {
            console.log(res.data)
            that.setData({
              erTradeList: res.data.errands,
              exTradeList: res.data.expressages,
              heTradeList: res.data.for_helps,
              seTradeList: res.data.second_hands
            })
          }
        })
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
    var arr_name;
    if (this.data.isSelected1) {
      var exList = this.data.exTradeList
      exList.splice(this.data.index,1)
      this.setData({
        exTradeList: exList
      })
      arr_name = "exTradeList"
    }
    console.log(this.data.exTradeList)
    console.log("confirm delete")
  },
  //长按删除
  deleteOrder: function (e) {
    this.setData({
      flag: !this.data.flag,
      index: e.currentTarget.dataset.id
    })
  },

  //初始加载
  /*OnLoadExpressage: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: 'http://' + baseUrl + ':8080/Load/OnLoadExpressage',
      success: function (res) {
        that.setData({ exTradeList: res.data })
        var exTradeList = that.data.exTradeList
        if (exTradeList.length > 5) {
          that.setData({ lastId1: exTradeList[exTradeList.length - 1].pid })
        }
        resolve()
      },
      fail: function (res) {
        console.log('fail to onload', res)
      }
    }))
    // console.log('load expressage')
  },
  OnLoadErrand: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: 'http://' + baseUrl + ':8080/Load/OnLoadErrands',
      success: function (res) {
        that.setData({ erTradeList: res.data })
        var erTradeList = that.data.erTradeList
        if (erTradeList.length > 5) {
          that.setData({ lastId2: erTradeList[erTradeList.length - 1].rid })
        }
        resolve()
      }
    }))
    // console.log('load errand')
  },
  OnLoadSeekhelp: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: 'http://' + baseUrl + ':8080/Load/OnLoadFor_help',
      success: function (res) {
        that.setData({ heTradeList: res.data })
        var heTradeList = that.data.heTradeList
        if (heTradeList.length > 5) {
          that.setData({ lastId3: heTradeList[heTradeList.length - 1].fid })
        }
        resolve()
      }
    }))
    //console.log('load seekhelp')
  },
  OnLoadSecondhand: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: 'http://' + baseUrl + ':8080/Load/OnLoadSecond_hand',
      success: function (res) {
        that.setData({ seTradeList: res.data })
        var seTradeList = that.data.seTradeList
        if (seTradeList.length > 5) {
          that.setData({ lastId4: seTradeList[seTradeList.length - 1].sid })
        }
        resolve()
      }
    }))
    //console.log('load secondhand')
  },*/
  //下拉加载
  downloadEx: function () {
    var that = this
    var tempUrl = 'http://' + baseUrl + ':8080/Load/downLoadExpressage?id='
    tempUrl += String(this.data.lastId1)
    wx.request({
      url: tempUrl,
      success: function (res) {
        if (res.data[res.data.length - 1].pid == that.data.lastId1) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({ lastId1: -1 })
        }
        else {
          console.log('获取新订单')
          var newList = that.data.exTradeList
          newList = newList.concat(res.data)
          console.log(res.data)
          that.setData({ exTradeList: newList })
          that.setData({ exTradeList: that.sortList(1, that.data.index) })
        }
      }
    })
    //this.setData({lastId1: -1})
  },
  downloadEr: function () {
    var that = this
    var tempUrl = 'http://' + baseUrl + ':8080/Load/downLoadErrands?id='
    tempUrl += String(this.data.lastId2)
    wx.request({
      url: tempUrl,
      success: function (res) {
        if (res.data[res.data.length - 1].rid == that.data.lastId2) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({ lastId2: -1 })
        }
        else {
          var newList = that.data.erTradeList
          newList = newList.concat(res.data)
          that.setData({ erTradeList: newList })
          that.setData({ erTradeList: that.sortList(2, that.data.index) })
        }
      }
    })
    //this.setData({lastId2: -1})
  },
  downloadHe: function () {
    var that = this
    var tempUrl = 'http://' + baseUrl + ':8080/Load/downLoadFor_help?id='
    tempUrl += String(this.data.lastId3)
    wx.request({
      url: tempUrl,
      success: function (res) {
        if (res.data[res.data.length - 1].fid == that.data.lastId3) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({ lastId3: -1 })
        }
        else {
          var newList = that.data.heTradeList
          newList = newList.concat(res.data)
          that.setData({ heTradeList: newList })
          that.setData({ heTradeList: that.sortList(3, that.data.index) })
        }
      }
    })
    //this.setData({lastId3: -1})
  },
  downloadSe: function () {
    var that = this
    var tempUrl = 'http://' + baseUrl + ':8080/Load/downLoadSecond_hand?id='
    tempUrl += String(this.data.lastId4)
    wx.request({
      url: tempUrl,
      success: function (res) {
        if (res.data[res.data.length - 1].sid == that.data.lastId4) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({ lastId4: -1 })
        }
        else {
          var newList = that.data.seTradeList
          newList = newList.concat(res.data)
          that.setData({ seTradeList: newList })
          that.setData({ seTradeList: that.sortList(4, that.data.index) })
        }
      }
    })
    //this.setData({lastId4: -1})
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

  //点击接单按钮
  receiptOrder: function (e) {
    var that = this
    var options = e.currentTarget.dataset
    var url = 'http://' + baseUrl + ':8080/Modified/AcceptIssue?type=' + options.type + '&id=' + options.id
    console.log(url)
    wx.request({
      url: url,
      header: { sessionId: that.data.sessionId },
      method: 'PUT',
      success: function (res) {
        console.log('接单成功')
        that.OnLoadExpressage()
        that.OnLoadErrand()
        that.OnLoadSeekhelp()
        that.OnLoadSecondhand()
      },
      fail: function (res) {
        console.log(res)
        wx.showToast({
          title: '接单失败',
          icon: 'none',
          duration: 3000
        })
      }
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
    console.log('sessionId: ', this.data.sessionId)
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
    that.setData({
      erTradeList: null,
      exTradeList: null,
      heTradeList: null,
      seTradeList: null
    })
    console.log(that.data.sessionId)
    wx.request({
      url: "http://172.18.32.138:8080/Load/Creation",
      header: { sessionId: that.data.sessionId },
      success: function (res) {
        console.log(res.data)
        that.setData({
          erTradeList: res.data.errands,
          exTradeList: res.data.expressages,
          heTradeList: res.data.for_helps,
          seTradeList: res.data.second_hands
        })
      }
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
    if (this.data.isSelected1 && this.data.lastId1 != -1)
      this.downloadEx()
    else if (this.data.isSelected2 && this.data.lastId2 != -1)
      this.downloadEr()
    else if (this.data.isSelected3 && this.data.lastId3 != -1)
      this.downloadHe()
    else if (this.data.isSelected4 && this.data.lastId4 != -1)
      this.downloadSe()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }

})