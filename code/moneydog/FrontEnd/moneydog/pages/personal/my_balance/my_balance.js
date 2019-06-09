var app = getApp()
Page({
  data: {
    sessionID:'',
    power:0
  },
  onLoad(options) {
    var that = this
    var id = wx.getStorageSync('SessionId')
    console.log(id)
    // Do some initialize when page load.
    that.setData({
      sessionID: id,
    }),
    wx.request({
      url: 'https://moneydog.club:3336/User/queryPower',
      method: 'GET',
      header: {
        "content-type": "application/x-www-form-urlencoded",
        sessionId: that.data.sessionID.toString()
      },
        success: function (res) {
          console.log(res.data)
          that.setData({
            power:res.data
          })
        }
      })
  },
  onReady() {
    // Do something when page ready.
  },
  onShow() {
    // Do something when page show.
  },
  onHide() {
    // Do something when page hide.
  },
  onUnload() {
    // Do something when page close.
  },
  add_money:function(){
    this.setData({
      power:this.data.power + 100
    }
    ),
    this.update_money()
  },
  sub_money: function () {
    if (this.data.power > 0)
    this.setData({
      power: this.data.power - 100
    }
    ),
    this.update_money()
  },
  update_money:function(){
    var that = this
  
    wx.request({
      url: 'https://moneydog.club:3336/User/setPower',
      method: 'POST',
      data:{
        power:that.data.power
      },
      header: {
        "content-type": "application/x-www-form-urlencoded", 
        sessionId: that.data.sessionID.toString()
        },
      success: function (res) {
          console.log(res.data)
        }
    })
  },
  showCard:function(){
    wx.showModal({
      title: '添加银行卡',
      content: '暂未支持此功能',
    })
  },
  goTobill:function(){
    wx.navigateTo({
      url: '../bill/bill',
    })
  }

})
