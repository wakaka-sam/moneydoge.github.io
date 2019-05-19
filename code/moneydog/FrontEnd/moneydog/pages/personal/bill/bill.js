var app = getApp()
Page({
  data: {
    money: 100
  },
  add_money: function () {
    this.setData({
      money: this.data.money + 100
    }
    )
    this.update_money()
  },
  sub_money: function () {
    if (this.data.money > 0)
      this.setData({
        money: this.data.money - 100
      }
      )
    this.update_money()
  },
  update_money: function () {
    var that = this
    sessionID = app.globalData.sessionID
    wx.request({
      url: '',
      method: 'POST',
      data: {
        money: that.data.money
      },
      header: { sessionID: sessionID }
    })
  },
  showCard: function () {
    wx.showModal({
      title: '添加银行卡',
      content: '暂未支持此功能',
    })
  },
  goTobill: function () {
    wx.navigateTo({
      url: '../bill/bill',
    })
  }

})
