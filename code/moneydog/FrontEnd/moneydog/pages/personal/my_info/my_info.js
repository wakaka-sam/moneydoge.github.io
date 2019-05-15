//logs.js

Page({
  data: {
    user_img:     "http://img.52z.com/upload/news/image/20180213/20180213062641_35687.jpg",
    nickname:'小明',
    name:'张小凡',
    school:'中山大学',
    phone:'15167496318',
    imageUrl:'../../../images/upload_picture.png'
  },
  setNickname: function(e){
    this.setData({
      nickname: e.detail.value
    })
  },
  setName: function (e) {
    this.setData({
      name: e.detail.value
    })
  },
  setSchool: function (e) {
    this.setData({
      school: e.detail.value
    })
  },
  setPhone: function (e) {
    this.setData({
      phone: e.detail.value
    })
  },
  change_info:function(){
  var that = this
  sessionID = app.globalData.sessionID
  wx.request({
    url: 'http://172.18.32.138:8080/Change/Info',
    data: {
      user_img:that.data.user_img,
      nickname: that.data.nickname,
      name: that.data.name,
      school: that.data.school,
      phone: that.data.phone,
      imageUrl:that.data.imageUrl
    },
    header: {
      'content-type': 'application/json', // 默认值
      sessionID:sessionID
    },
    success: function (res) {
      console.log(res.data)
    }
  })
  },
  up_schoolcard:function(){
    var that = this
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        console.log(tempFilePaths[0])
      }
    })
    wx.uploadFile({
      url: '',
      filePath: 'res.tempFilePaths[0]',
      name: 'img',
      success:function(res){
        var t = JSON.parse(res.data);
        consloe.log(t.imageUrl)
        var url = 'http://172.18.32.138:8080' + t.imageUrl;
        that.setData({
          imageUrl:url
        })
      }
    })
  }
})
