var app = getApp()
Page({
  data:{
    sessionID:'',
    list:[],
  },

  onLoad(options) {
    var id = wx.getStorageSync('SessionId')
    console.log(id)
    var that = this

    that.setData({
      sessionID:id,
    }),
    wx.request({
      url: 'https://moneydog.club:3336/User/getUnstated',
      method: 'GET',
      header: {
        "content-type": "application/x-www-form-urlencoded",
        sessionId: that.data.sessionID.toString()
      },
      success(res){
        console.log("管理员登陆成功")
        if(res.data.statecode == 1){
          that.setData({
            list:res.data.user
          })
          console.log(that.data.list)
        }
        }
    })
  },
  pass:function(e){
    var id = e.currentTarget.dataset.id
    console.log(id);

    wx.request({
      url: 'https://moneydog.club:3336/User/changeState',
      method:'POST',
      header: {
        "content-type": "application/x-www-form-urlencoded",
        sessionId: this.data.sessionID.toString()
      },
      data:{
        openid:id,
        state:1
      },
      success:function(res){
        if (res) {
          wx.showModal({
            title: '审核通过',
            content: '',
          })
        }
      }
    })
  },
  failure: function (e) {
    var id = e.currentTarget.dataset.id
    console.log(id);

    wx.request({
      url: 'https://moneydog.club:3336/User/changeState',
      method: 'POST',
      header: {
        "content-type": "application/x-www-form-urlencoded",
        sessionId: this.data.sessionID.toString()
      },
      data: {
        openid: id,
        state: -1
      },
      success: function (res) {
        if (res) {
          wx.showModal({
            title: '信息异常',
            content: '请重新上传',
          })
        }
      }
    })
  }
  
  
})