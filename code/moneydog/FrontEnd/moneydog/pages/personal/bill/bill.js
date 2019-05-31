var app = getApp()
Page({
  data:{
    list:[],
    sessionID:'',
  },
  onLoad(options) {
    var that = this
    // Do some initialize when page load.
    that.setData({
      sessionID: '847694c4-14dd-47b2-8922-facd8e379f47',
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
