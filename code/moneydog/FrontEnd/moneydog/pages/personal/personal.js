var app = getApp()
Page({
  data: {
    sessionID:'',
    userInfo:{},
  },
  onLoad(options) {
    var id = wx.getStorageSync('SessionId')
    console.log(id)
    
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
      })
    }
    // Do some initialize when page load.
    this.setData({
      sessionID: id,
    })
  },
  goTo_info: function () {
    wx.navigateTo({
      url: 'my_info/my_info',
    })
  },
  goTo_balance: function () {
    wx.navigateTo({
      url: 'my_balance/my_balance',
    })
  },
  goTo_service: function () {
    wx.navigateTo({
      url: 'service/service',
    })
  },
  goTo_check: function () {
    console.log(this.data.userInfo.nickName)
    if(this.data.userInfo.nickName == '惊鸿一瞥'){
      wx.navigateTo({
        url: 'check/check',
      })
    }
    else{
      wx.showModal({
        title: '您不是管理员',
        content: '只有管理员可以使用此项功能',
      })
  }
  }

})
