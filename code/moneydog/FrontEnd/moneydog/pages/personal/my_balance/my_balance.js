var app = getApp()
Page({
  data: {
    power:100
  },
  add_money:function(){
    this.setData({
      power:this.data.power + 100
    }
    )
    this.update_money()
  },
  sub_money: function () {
    if (this.data.money > 0)
    this.setData({
      power: this.data.power - 100
    }
    )
    this.update_money()
  },
  update_money:function(){
    var that = this
    
    wx.request({
      url: 'http://119.23.218.7:6666/User/getPower',
      method: 'POST',
      data:{
        power: that.data.power
      },
      header:{}
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
