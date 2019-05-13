// pages/receipt/receipt.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    selctShow: false,//控制下拉列表的显示隐藏，false隐藏、true显示
    selectData: ['薪酬筛选（从高到低）', '薪酬筛选（从低到高）'],//下拉列表的数据
    index: 0,//选择的下拉列表下标
    isSelected1: false,//默认快递
    isSelected2: true,//跑腿
    isSelected3: false,//求助
    isSelected4: false,//闲置
    isSelected5: false,//问卷
    lastId1: -1,//获取订单列表后得到
    lastId2: -1,//列表最后一个元素的
    lastId3: -1,//订单号，并存起来，
    lastId4: -1,//默认值-1，当得到所
    lastId5: -1,//有订单后也置为-1
    exTradeList: [
      {
        "express_loc":"明德园6号",
        "arrive_time":"2019-05-03",
        "num":4,
        "pay":10,
        "loc":"至善园2号666",
        "pid":4
      },
      {
        "express_loc": "明德园6号",
        "arrive_time": "2019-05-04",
        "num": 1,
        "pay": 15,
        "loc": "至善园2号666",
        "pid": 5
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

  // 点击下拉显示框
  selectTap() {
    this.setData({
      selectShow: !this.data.selectShow
    });
  },
  // 点击下拉列表
  optionTap(e) {
    let Index = e.currentTarget.dataset.index;//获取点击的下拉列表的下标
    this.setData({
      index: Index,
      selectShow: !this.data.selectShow
    });
    this.setData({
      exTradeList: this.sortList(1, Index),
      erTradeList: this.sortList(2, Index),
      heTradeList: this.sortList(3, Index),
      seTradeList: this.sortList(4, Index)
    })
  },
  //对订单列表进行排序
  sortList: function(i, index){
    if(i == 1){//i等于1时，对快递订单列表排序
     var tradeList = this.data.exTradeList
    }
    else if(i == 2){//跑腿
      var tradeList = this.data.erTradeList
    }
    else if(i == 3) {//求助
      var tradeList = this.data.heTradeList
    }
    else { //闲置
      var tradeList = this.data.seTradeList
    }
    // console.log()
    if (index == 1) {
      tradeList.sort(function(a, b) {
        if (a.pay < b.pay)
          return -1;
        else if (a.pay > b.pay)
          return 1;
        return 0;
      })
    }
    else {
      tradeList.sort(function (a, b) {
        if (a.pay > b.pay)
          return -1;
        else if (a.pay < b.pay)
          return 1;
        return 0;
      })
    }
    return tradeList
  },

  //初始加载
  OnLoadExpressage: function(){
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: 'http://172.18.32.138:8080/Load/OnLoadExpressage',
      success: function(res) {
        that.setData({exTradeList:res.data})
        var exTradeList = that.data.exTradeList
        if(exTradeList.length > 0) {
          that.setData({lastId1: exTradeList[exTradeList.length-1].pid})
        }
        resolve()
      }
    }))
    // console.log('load expressage')
  },
  OnLoadErrand: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: 'http://172.18.32.138:8080/Load/OnLoadErrands',
      success: function (res) {
        that.setData({ erTradeList: res.data })
        var erTradeList = that.data.erTradeList
        if(erTradeList.length > 0) {
          that.setData({lastId2: erTradeList[erTradeList.length-1].rid})
        }
        resolve()
      }
    }))
    // console.log('load errand')
  },
  OnLoadSeekhelp: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: 'http://172.18.32.138:8080/Load/OnLoadFor_help',
      success: function (res) {
        that.setData({ heTradeList: res.data })
        var heTradeList = that.data.heTradeList
        if(heTradeList.length > 0) {
          that.setData({lastId3: heTradeList[heTradeList.length-1].fid})
        }
        resolve()
      }
    }))
    //console.log('load seekhelp')
  },
  OnLoadSecondhand: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: 'http://172.18.32.138:8080/Load/OnLoadSecond_hand',
      success: function (res) {
        that.setData({ seTradeList: res.data })
        var seTradeList = that.data.seTradeList
        if(seTradeList.length > 0) {
          that.setData({lastId4: seTradeList[seTradeList.length-1].sid})
        }
        resolve()
      }
    }))
    //console.log('load secondhand')
  },
  //下拉加载
  downloadEx: function(){
    var that = this
    var tempUrl = 'http://172.18.32.138:8080/Load/downLoadExpressage?id='
    tempUrl += String(this.data.lastId1)
    wx.request({
      url: tempUrl,
      success: function (res) {
        var newList = that.data.exTradeList
        newList = newList.concat(res.data)
        console.log(res.data)
        that.setData({exTradeList: newList})
        that.setData({exTradeList: that.sortList(1, that.data.index)})
      }
    })
    this.setData({lastId1: -1})
  },
  downloadEr: function () {
    var that = this
    var tempUrl = 'http://172.18.32.138:8080/Load/downLoadErrands?id='
    tempUrl += String(this.data.lastId2)
    wx.request({
      url: tempUrl,
      success: function(res) {
        var newList = that.data.erTradeList
        newList = newList.concat(res.data)
        that.setData({erTradeList: newList})
        that.setData({ erTradeList: that.sortList(2, that.data.index) })
      }
    })
    this.setData({lastId2: -1})
  },
  downloadHe: function () {
    var that = this
    var tempUrl = 'http://172.18.32.138:8080/Load/downLoadFor_help?id='
    tempUrl += String(this.data.lastId3)
    wx.request({
      url: tempUrl,
      success: function(res) {
        var newList = that.data.heTradeList
        newList = newList.concat(res.data)
        that.setData({heTradeList: newList})
        that.setData({ heTradeList: that.sortList(3, that.data.index) })
      }
    })
    this.setData({lastId3: -1})
  },
  downloadSe: function () {
    var that = this
    var tempUrl = 'http://172.18.32.138:8080/Load/downLoadSecond_hand?id='
    tempUrl += String(this.data.lastId4)
    wx.request({
      url: tempUrl,
      success: function(res) {
        var newList = that.data.seTradeList
        newList = newList.concat(res.data)
        that.setData({seTradeList: newList})
        that.setData({ seTradeList: that.sortList(4, that.data.index) })
      }
    })
    this.setData({lastId4: -1})
  },

  //跳转详情页面
  showDetail: function(e) {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    this.OnLoadExpressage().then(() => that.OnLoadErrand()).then(
      () => that.OnLoadSeekhelp()).then(() => that.OnLoadSecondhand()).then(() => {
      that.setData({
        exTradeList: that.sortList(1, 0),
        erTradeList: that.sortList(2, 0),
        heTradeList: that.sortList(3, 0),
        seTradeList: that.sortList(4, 0)
      })})
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