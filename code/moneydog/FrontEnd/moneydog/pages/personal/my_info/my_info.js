//logs.js
var app = getApp()
Page({
  data: {
    user_img:     'http://img.52z.com/upload/news/image/20180213/20180213062641_35687.jpg',
    nickname:'小明',
    name:'张小凡',
    school:'中山大学',
    phone:'15167496318',
    schoolCard:'../../../images/upload_picture.png'
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
  
  wx.request({
    url: 'http://119.23.218.7:6666/User/Update',
    method:'POST',
    data: {
      user_img:that.data.user_img,
      nickname: that.data.nickname,
      name: that.data.name,
      school: that.data.school,
      phone: that.data.phone,
      schoolCard:that.data.schoolCard
    },
    header: {
      
    },
    success: function (res) {
      console.log(res.data)
    }
  })
  },
  upSchoolcard:function(){
    var that = this
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        that.setData({
          schoolCard:tempFilePaths[0]
        })
        wx.uploadFile({
          url: 'http://119.23.218.7:8080/File/Upload',
          filePath: that.data.schoolCard,
          name: 'img',
          success: function (res) {
            var t = JSON.parse(res.data);
            console.log(t.imageUrl)
            var url = 'http://119.23.218.7:8080/' + t.imageUrl;
            that.setData({
              schoolCard: url
            })
          }
        })
      }
    })  
  },
  upUserimag: function () {
    var that = this
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        that.setData({
          user_img: tempFilePaths[0]
        })
        wx.uploadFile({
          url: 'http://119.23.218.7:8080/File/Upload',
          filePath: that.data.user_img,
          name: 'img',
          success: function (res) {
            var t = JSON.parse(res.data);
            console.log(t.imageUrl)
            var url = 'http://119.23.218.7:8080/' + t.imageUrl;
            that.setData({
              user_img: url
            })
          }
        })
      }
    })
  }

})
