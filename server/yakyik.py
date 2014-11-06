import flask
import json
import time

from flask import jsonify, request
from flask.ext.pymongo import PyMongo
from bson import json_util
from bson.objectid import ObjectId

application = flask.Flask(__name__)
application.debug=True


# Get MongoDB client
mongo = PyMongo(application)
#mongo.db.messages.ensureIndex( { 'location' : '2dsphere' } )

@application.route('/messages', methods=['GET'])
def get_messages():
  longitude = request.args.get('longitude', None)
  latitude = request.args.get('latitude', None)
  if not longitude or not latitude:
    return 'longitude or latitude missing', 400

  nearby_messages = mongo.db.messages.find(
    {
      'location': {
        '$near': {
          '$geometry': {
            'type': 'Point',
            'coordinates': [ float(longitude), float(latitude) ]
          },
          '$maxDistance': 2000,
          '$minDistance': 0
        }
      }
    },
    {
      'location': False
    }
    ).sort("time_posted", -1)
  return str(json.dumps({'messages':list(nearby_messages)}, default=json_util.default)), 200


@application.route('/messages', methods=['POST'])
def post_message():
  message = request.form.get('message')
  longitude = float(request.form.get('longitude', None))
  latitude = float(request.form.get('latitude', None))

  if not message or not longitude or not latitude:
    return 'required elements not present', 400

  author = request.form.get('author', None)
  timestamp = int(time.time() * 1000)

  new_message = {
    'location': { 'type': 'Point', 'coordinates': [ longitude, latitude ] },
    'message': message,
    'author': author,
    'score': 0,
    'time_posted': timestamp
  }

  mongo.db.messages.insert(new_message)

  return 'success', 200


@application.route('/messages/upvote', methods=['POST'])
def upvote_message():
  message_id = request.form.get('message_id')
  if not message_id:
    return 'message_id missing', 400

  message = mongo.db.messages.update({'_id': ObjectId(message_id)}, {'$inc': {'score': 1}})
  return 'success', 200


@application.route('/messages/downvote', methods=['POST'])
def downvote_message():
  message_id = request.form.get('message_id')
  if not message_id:
    return 'message_id missing', 400

  message = mongo.db.messages.update({'_id': ObjectId(message_id)}, {'$inc': {'score': -1}})
  return 'success', 200


if __name__ == '__main__':
    application.run(host='0.0.0.0', debug=True)

