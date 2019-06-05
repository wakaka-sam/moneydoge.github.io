var app = getApp()
Page({
  data: {
    admini:true,
    sessionID:'',
    school:'中山大学',
    realname: '小明',
    falsename:'',
    phoneNum: 15167496318,
    user_img: "http://img.52z.com/upload/news/image/20180213/20180213062641_35687.jpg",
    
  },
  onLoad(options) {
    var that = this
    const id = wx.getStorageSync('SessionId')
    console.log(id)
    // Do some initialize when page load.
    that.setData({
      sessionID: '847694c4-14dd-47b2-8922-facd8e379f47',
      admini: true,
    }),
    wx.request({
      url: 'https://moneydog.club:3336/User/Info',
        method: 'GET',
        header: {
          "content-type": "application/x-www-form-urlencoded",
          sessionId: that.data.sessionID.toString()
        },
        success: function(res) {
          console.log(res.data)
        }
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
    var that = this
    if(that.data.admini == false){
    wx.showModal({
      title: '您不是管理员',
      content: '只有管理员可以使用此项功能',
    })
    }
    else{
    wx.navigateTo({
      url: 'check/check',
    })
  }
  }

})
