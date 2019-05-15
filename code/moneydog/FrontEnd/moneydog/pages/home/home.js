//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    searchUrl: '/images/search-icon.png',
    showDialog: false //弹窗
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  //跳转到快递代拿接单界面
  goToReceiptPage1: function() {
    wx.navigateTo({
      url: '../receipt/receipt?id=1',
    })
  },

  goToPublishPage2: function () {
    wx.navigateTo({
      url: '../publish/publish?id=2',//跳转的时候传值，在跳转到的页面的js的page处理id
    })
  },
  goToPublishPage3: function () {
    wx.navigateTo({
      url: '../publish/publish?id=3',//跳转的时候传值，在跳转到的页面的js的page处理id
    })
  },
  goToPublishPage4: function () {
    wx.navigateTo({
      url: '../publish/publish?id=4',//跳转的时候传值，在跳转到的页面的js的page处理id
    })
  },

  goToWenjuanPage: function() {
    wx.navigateTo({
      url: '../questionnaire/questionnaire',
    })
  },
  toggleDialog() {
    this.setData({
      showDialog: !this.data.showDialog
    })
  },
  onLoad: function () {
    wx.hideTabBar({})
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
      wx.showTabBar({})
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
        wx.showTabBar({})
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
          wx.showTabBar({})
        }
      })
    }
  },
  //跳转到跑腿接单界面
  goToReceiptPage2: function () {
    wx.navigateTo({
      url: '../receipt/receipt?id=2',
    })
  },
  //跳转到求助接单界面
  goToReceiptPage3: function () {
    wx.navigateTo({
      url: '../receipt/receipt?id=3',
    })
  },
  //跳转到闲置接单界面
  goToReceiptPage4: function () {
    wx.navigateTo({
      url: '../receipt/receipt?id=4',
    })
  },
  //跳转到问卷接单界面
  goToReceiptPage5: function () {
    wx.navigateTo({
      url: '../receipt/receipt?id=5',
    })
  },
  //跳转到快递代拿发布界面
  goToPublishPage1: function() {
    wx.navigateTo({
      url: '../publish/publish?id=1',
    })
  },
  //跳转到求助发布界面
  goToPublishPage2: function () {
    wx.navigateTo({
      url: '../publish/publish?id=2',
    })
  },
  //跳转到跑腿发布界面
  goToPublishPage3: function () {
    wx.navigateTo({
      url: '../publish/publish?id=3',
    })
  },
  //跳转到闲置发布界面
  goToPublishPage4: function () {
    wx.navigateTo({
      url: '../publish/publish?id=4',
=======
  //跳转到快递代拿发布界面（可编辑删除）
  goToPublishPage1: function () {
    wx.navigateTo({
      url: '../publish/publish?id=1',//跳转的时候传值，在跳转到的页面的js的page处理id

    })
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
    wx.showTabBar({})
    if (app.globalData.code) {
      // ------ 发送凭证 ------
      var t = {
        code: app.globalData.code,
        nickName: e.detail.userInfo.nickName,
        avatarUrl: e.detail.userInfo.avatarUrl,
        gender: "1"//e.detail.userInfo.gender
      }
      console.log(t)
      wx.request({
        url: 'http://172.18.32.138:8080/Create/User',
        data: t,
        method: 'POST',
        header: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        success: function (res) {
          console.log(res)
          if (res.statusCode == 200) {
            console.log("获取到的openid为：" + res.data)
            app.globalData.openid = res.data
            wx.setStorageSync('openid', res.data)
          } else {
            console.log('结果：' + res.errMsg)
          }
        },
      })
    } else {
      console.log('获取用户登录失败：' + res.errMsg);
    }
  }
})
