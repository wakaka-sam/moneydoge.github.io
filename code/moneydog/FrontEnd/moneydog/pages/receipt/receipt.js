const baseUrl = 'https://moneydog.club:3030/'
// pages/receipt/receipt.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    sessionId: '847694c4-14dd-47b2-8922-facd8e379f47',
    selctShow: false,//控制下拉列表的显示隐藏，false隐藏、true显示
    selectData: ['薪酬筛选（从高到低）', '薪酬筛选（从低到高）'],//下拉列表的数据
    index: 0,//选择的下拉列表下标
    isSelected1: true,//默认快递
    isSelected2: false,//跑腿
    isSelected3: false,//求助
    isSelected4: false,//闲置
    isSelected5: false,//问卷
    lastId1: -1,//获取订单列表后得到
    lastId2: -1,//列表最后一个元素的
    lastId3: -1,//订单号，并存起来，
    lastId4: -1,//默认值-1，当得到所
    lastId5: -1,//有订单后也置为-1
    exTradeList: [/*
      {
        "express_loc":"明德园6号",
        "arrive_time":"2019-05-03",
        "num":4,
        "pay":10,
        "loc":"至善园2号666",
        "pid":4,
        "remark":"none",
        "issue_time":"2019-05-3",
        "state":0
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
      }*/],
    erTradeList: [],
    heTradeList: [],
    seTradeList: [],
    quTradeList: [/*{
      "qid": 1,
      "name": "调查问卷",
      "description": "饭堂满意度",
      "pay": 2
    },
    {
      "qid": 0,
      "name": "调查问卷",
      "description": "饭堂满意度",
      "pay": 1
    }*/]
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
      seTradeList: this.sortList(4, Index),
      quTradeList: this.sortList(5, Index)
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
    else if(i == 4) {//闲置
      var tradeList = this.data.seTradeList
    }
    else {           //问卷
      var tradeList = this.data.quTradeList
    }
    if (tradeList.length == 0) 
      return tradeList
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
      url: baseUrl + 'Load/OnLoadExpressage',
      success: function(res) {
        that.setData({exTradeList:res.data})
        var exTradeList = that.data.exTradeList
        for (var i = 0; i < exTradeList.length; i++) {
          exTradeList[i].arrive_time = that.convertUTCTimeToLocalTime(exTradeList[i].arrive_time)
          exTradeList[i].issue_time = that.convertUTCTimeToLocalTime(exTradeList[i].issue_time)
        }
        if(exTradeList.length > 5) {
          that.setData({lastId1: exTradeList[exTradeList.length-1].pid})
        }
        resolve()
      },
      fail: function(res) {
        console.log('fail to onload',res)
      }
    }))
    // console.log('load expressage')
  },
  OnLoadErrand: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: baseUrl + 'Load/OnLoadErrands',
      success: function (res) {
        that.setData({ erTradeList: res.data })
        var erTradeList = that.data.erTradeList
        for (var i = 0; i < erTradeList.length; i++) {
          erTradeList[i].ending_time = that.convertUTCTimeToLocalTime(erTradeList[i].ending_time)
          erTradeList[i].issue_time = that.convertUTCTimeToLocalTime(erTradeList[i].issue_time)
        }
        if(erTradeList.length > 5) {
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
      url: baseUrl + 'Load/OnLoadFor_help',
      success: function (res) {
        that.setData({ heTradeList: res.data })
        var heTradeList = that.data.heTradeList
        for (var i = 0; i < heTradeList.length; i++) {
          heTradeList[i].ending_time = that.convertUTCTimeToLocalTime(heTradeList[i].ending_time)
          heTradeList[i].issue_time = that.convertUTCTimeToLocalTime(heTradeList[i].issue_time)
        }
        if(heTradeList.length > 5) {
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
      url: baseUrl + 'Load/OnLoadSecond_hand',
      success: function (res) {
        that.setData({ seTradeList: res.data })
        var seTradeList = that.data.seTradeList
        for (var i = 0; i < seTradeList.length; i++) {
          seTradeList[i].ending_time = that.convertUTCTimeToLocalTime(seTradeList[i].ending_time)
          seTradeList[i].issue_time = that.convertUTCTimeToLocalTime(seTradeList[i].issue_time)
        }
        if(seTradeList.length > 5) {
          that.setData({lastId4: seTradeList[seTradeList.length-1].sid})
        }
        resolve()
      }
    }))
    //console.log('load secondhand')
  },
  //加载问卷
  OnLoadQuestionair: function () {
    var that = this
    return new Promise((resolve, rej) => wx.request({
      url: baseUrl + 'Create/OnLoadQuestionair',
      success: function (res) {
        that.setData({ quTradeList: res.data })
        var quTradeList = that.data.quTradeList
        if(quTradeList.length > 5) {
          that.setData({lastId5: quTradeList[quTradeList.length-1].qid})
        }
        resolve()
      }
    }))
    console.log('OnLoadQuestionair')
  },
  //下拉加载
  downloadEx: function(){
    var that = this
    var tempUrl = baseUrl + 'Load/downLoadExpressage?id='
    tempUrl += String(this.data.lastId1)
    wx.request({
      url: tempUrl,
      success: function (res) {
        if (res.data[res.data.length-1].pid == that.data.lastId1) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({lastId1: -1})
        }
        else {
          console.log('获取新订单')
          var newList = that.data.exTradeList
          var res_data = res.data
          for (var i = 0; i < res_data.length; i++) {
            res_data[i].arrive_time = that.convertUTCTimeToLocalTime(res_data[i].arrive_time)
            res_data[i].issue_time = that.convertUTCTimeToLocalTime(res_data[i].issue_time)
          }
          newList = newList.concat(res_data)
          console.log(res.data)
          that.setData({exTradeList: newList, lastId1: res.data[res.data.length-1].pid})
          that.setData({exTradeList: that.sortList(1, that.data.index)})
        }
      }
    })
    //this.setData({lastId1: -1})
  },
  downloadEr: function () {
    var that = this
    var tempUrl = baseUrl + 'Load/downLoadErrands?id='
    tempUrl += String(this.data.lastId2)
    wx.request({
      url: tempUrl,
      success: function(res) {
        if (res.data[res.data.length-1].rid == that.data.lastId2) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({lastId2: -1})
        }
        else {
          console.log('获取新订单')
          var newList = that.data.erTradeList
          var res_data = res.data
          for (var i = 0; i < res_data.length; i++) {
            res_data[i].ending_time = that.convertUTCTimeToLocalTime(res_data[i].ending_time)
            res_data[i].issue_time = that.convertUTCTimeToLocalTime(res_data[i].issue_time)
          }
          newList = newList.concat(res_data)
          that.setData({erTradeList: newList, lastId2: res.data[res.data.length-1].rid})
          that.setData({ erTradeList: that.sortList(2, that.data.index) })
        }
      }
    })
    //this.setData({lastId2: -1})
  },
  downloadHe: function () {
    var that = this
    var tempUrl = baseUrl + 'Load/downLoadFor_help?id='
    tempUrl += String(this.data.lastId3)
    wx.request({
      url: tempUrl,
      success: function(res) {
        if (res.data[res.data.length-1].fid == that.data.lastId3) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({lastId3: -1})
        }
        else {
          console.log('获取新订单')
          var newList = that.data.heTradeList
          var res_data = res.data
          for (var i = 0; i < res_data.length; i++) {
            res_data[i].ending_time = that.convertUTCTimeToLocalTime(res_data[i].ending_time)
            res_data[i].issue_time = that.convertUTCTimeToLocalTime(res_data[i].issue_time)
          }
          newList = newList.concat(res_data)
          that.setData({heTradeList: newList, lastId3: res.data[res.data.length-1].fid})
          that.setData({ heTradeList: that.sortList(3, that.data.index) })
        }
      }
    })
    //this.setData({lastId3: -1})
  },
  downloadSe: function () {
    var that = this
    var tempUrl = baseUrl + 'Load/downLoadSecond_hand?id='
    tempUrl += String(this.data.lastId4)
    wx.request({
      url: tempUrl,
      success: function(res) {
        if (res.data[res.data.length-1].sid == that.data.lastId4) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({lastId4: -1})
        }
        else {
          console.log('获取新订单')
          var newList = that.data.seTradeList
          var res_data = res.data
          for (var i = 0; i < res_data.length; i++) {
            res_data[i].ending_time = that.convertUTCTimeToLocalTime(res_data[i].ending_time)
            res_data[i].issue_time = that.convertUTCTimeToLocalTime(res_data[i].issue_time)
          }
          newList = newList.concat(res_data)
          that.setData({seTradeList: newList, lastId4: res.data[res.data.length-1].sid})
          that.setData({ seTradeList: that.sortList(4, that.data.index) })
        }
      }
    })
    //this.setData({lastId4: -1})
  },
  //上拉加载问卷
  downloadQu: function () {
    var that = this 
    var tempUrl = baseUrl + 'Create/downLoadQuestionair?id='
    tempUrl += String(this.data.lastId5)
    wx.request({
      url: tempUrl,
      success: function (res) {
        if (res.data[res.data.length-1].qid == that.data.lastId5) {
          console.log('目前已经获取所有订单无法再更新')
          that.setData({lastId5: -1})
        }
        else {
          console.log('获取新订单')
          var newList = that.data.quTradeList
          newList = newList.concat(res.data)
          that.setData({quTradeList: newList, lastId5: res.data[res.data.length-1].qid})
          that.setData({ quTradeList: that.sortList(5,that.data.index) })
        }
      }
    })
  },

  //跳转详情页面
  showDetail: function(e) {
    var trade = e.currentTarget.dataset
    var json = trade.json
    var id = trade.id
    wx.setStorageSync('detailJson', json)
    console.log(wx.getStorageSync('detailJson'))
    console.log('跳转id:', id)
    wx.navigateTo({
      url: './../details/details?id=' + id
    })
  },

  //点击接单按钮
  receiptOrder: function(e) {
    var that = this
    var options = e.currentTarget.dataset
    var url = baseUrl + 'Modified/AcceptIssue?type=' + options.type + '&id=' + options.id
    console.log(url)
    wx.request({
      url: url,
      header: {sessionId: that.data.sessionId},
      method: 'PUT',
      success: function(res) {
        console.log(res)
        wx.showToast({
          title: '接单成功',
          icon: 'success',
          duration: 3000
        })
        that.OnLoadExpressage()
        that.OnLoadErrand()
        that.OnLoadSeekhelp()
        that.OnLoadSecondhand()
      },
      fail: function(res) {
        console.log(res)
        wx.showToast({
          title: '接单失败',
          icon: 'none',
          duration: 3000
        })
      }
    })
  },

  //Date读取
  convertUTCTimeToLocalTime: function (UTCDateString) {
        if(!UTCDateString){
          return '-';
        }
        function formatFunc(str) {    //格式化显示
          return str > 9 ? str : '0' + str
        }
        var date2 = new Date(UTCDateString);     //这步是关键
        var year = date2.getFullYear();
        var mon = formatFunc(date2.getMonth() + 1);
        var day = formatFunc(date2.getDate());
        var hour = date2.getHours();
        var noon = hour >= 12 ? 'PM' : 'AM';
        hour = hour>=12?hour-12:hour;
        hour = formatFunc(hour);
        var min = formatFunc(date2.getMinutes());
        var dateStr = year+'-'+mon+'-'+day+' '+noon +' '+hour+':'+min;
        return dateStr;
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const app = getApp()
    const sessionId = wx.getStorageSync('SessionId')
    if (sessionId){
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
    //onload请求是异步的，而我们需要收到所有数据再进行排序
    //所以需要用到promise来保证onload一定在sort之前
    this.OnLoadExpressage().then(() => that.OnLoadErrand()).then(
      () => that.OnLoadSeekhelp()).then(() => that.OnLoadSecondhand()).then(
      () => that.OnLoadQuestionair()).then(() => {
      that.setData({
        exTradeList: that.sortList(1, 0),
        erTradeList: that.sortList(2, 0),
        heTradeList: that.sortList(3, 0),
        seTradeList: that.sortList(4, 0),
        quTradeList: that.sortList(5, 0)
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
    if (this.data.isSelected1 && this.data.lastId1 != -1)
      this.downloadEx()
    else if (this.data.isSelected2 && this.data.lastId2 != -1)
      this.downloadEr()
    else if (this.data.isSelected3 && this.data.lastId3 != -1)
      this.downloadHe()
    else if (this.data.isSelected4 && this.data.lastId4 != -1)
      this.downloadSe()
    else if (this.data.isSelected5 && this.data.lastId5 != -1)
      this.downloadQu()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }

})