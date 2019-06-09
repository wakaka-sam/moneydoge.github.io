var app = getApp()
Page({
  data:{
    list:[],
    sessionID:'',
  },
  onLoad(options) {
    var that = this
    var id = wx.getStorageSync('SessionId')
    console.log(id)
    // Do some initialize when page load.
    that.setData({
      sessionID: id,
    }),
    // Do some initialize when page load.
    that.setData({
      sessionID: id,
    })
     that.getList()
  },
  getList:function(){
    var that = this
    wx.request({
      url: 'https://moneydog.club:3336/History/getHistory',
      method: 'GET',
      header: {
        "content-type": "application/x-www-form-urlencoded",
        sessionId: that.data.sessionID.toString()
      },
      success: function (res) {
        console.log(res.data)
        var temp = res.data
        console.log(temp)
        console.log(temp.data[0])
        that.setData({
          list:temp.data
        })
      }
    })
  }
  

})
