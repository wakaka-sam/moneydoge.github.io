var app = getApp()
Page({
  data:{
    sessionID:'',
    state:'',
    openid:'',
    realname:'',
    image_url:'../../../images/图片.png'
  },
  onLoad(options) {
    var that = this
    that.setData({
      sessionID: '847694c4-14dd-47b2-8922-facd8e379f47',
    }),
    wx.request({
      url: 'https://moneydog.club:3336/User/getUnstated',
      method: 'GET',      
      header: {
          "content-type": "application/x-www-form-urlencoded",
          sessionId: that.data.sessionID.toString()
        },
      success(res){
        console.log(res.data)
        if(res.data.statecode == 1){
          that.setData({
            openid:res.data.user[0].openid,
            realneme: res.data.user[0].realname,
            image_url: res.data.user[0].image_url,
          })
        }
        }
    })
  },
  confirm:function(){
    var that = this
    that.setData({
      state:1,
      sessionID: '847694c4-14dd-47b2-8922-facd8e379f47',
    }),
    wx.request({
      url: 'https://moneydog.club:3336/User/changeState',
      method: 'POST',
      header: {
        "content-type": "application/x-www-form-urlencoded",
        sessionId: that.data.sessionID.toString()
      },
      data:{
        openid:that.data.openid,
        state:that.data.state
      },
      success(res){
        console.log(res.data)
      }
    }),
    wx.navigateBack({
      delta: 1
    }) 
  },
  failing:function(){
    var that = this
    that.setData({
      state: -1,
      sessionID: '847694c4-14dd-47b2-8922-facd8e379f47',
    }),
      wx.request({
        url: 'https://moneydog.club:3336/User/changeState',
        method: 'POST',
        header: {
          "content-type": "application/x-www-form-urlencoded",
          sessionId: that.data.sessionID.toString()
        },
        data: {
          openid: that.data.openid,
          state: that.data.state
        },
        success(res) {
          console.log(res.data)
        }
      }),
    wx.navigateBack({
      delta: 1
    }) 
  }
})